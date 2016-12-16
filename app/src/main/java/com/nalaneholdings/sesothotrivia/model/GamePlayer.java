package com.nalaneholdings.sesothotrivia.model;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.Progress;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;
import com.nalaneholdings.sesothotrivia.model.bean.PlayerFactory;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class governs the rules of playing a gameStatus
 * Created by ntholi.nkhatho on 2016/09/12.
 */
public class GamePlayer extends Progress {

    private static final String TAG = "GamePlayer";
    private GameStatus gameStatus;
    private List<List<Question>> questions;
    private String message;
    private QuestionDownloadable questionLoader;


    public GamePlayer(Context context){
        super(context);
        gameStatus = PlayerFactory.getPlayer().getGameStatus();
        questions = new ArrayList<>();{
            //add the first element, which will not contain any value
            questions.add(new ArrayList<Question>());
        }
        if(context instanceof QuestionDownloadable){
            questionLoader = (QuestionDownloadable) context;
        }
        showProgressDialog();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Question.NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addQuestion(dataSnapshot);

                if(questions != null && questions.size() > (gameStatus.getLevel() + 1)){
                    hideProgressDialog();
                    if(questionLoader != null)
                        questionLoader.onQuestionDownloaded();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Add questions to the question list, and make sure to add only questions that have not been answered yet
     * @param data DataSnapshot
     */
    private void addQuestion(DataSnapshot data) {
        Question question = data.getValue(Question.class);
        if (!gameStatus.getAnsweredQuestions().contains(data.getKey())) {
            question.setId(data.getKey());
            if(questions.size() <= question.getLevel()){
                questions.add(new ArrayList<Question>());
            }
            questions.get(question.getLevel()).add(question);
        }
    }

    public int getLevel() {
        return gameStatus.getLevel();
    }


    /**
     * a class that implements this interface will only access only when they are fully downloaded
     * from the fire base
     */
    public interface QuestionDownloadable { // TODO: 2016/12/15 Rename this to QuestionDownloadable
        public void onQuestionDownloaded();
    }

    public GameStatus getGameStatus(){return gameStatus;}

    public String getMessage() {
        return message;
    }

    /**
     * Return a random question within questions within the player's game status level
     * And increase level if all questions within level are answered
     * @return Question
     */
    public Question getQuestion(){
        if(gameStatus.getAnsweredQuestions().size() >= questions.get(getLevel()).size()){
            gameStatus.increaseLevel();
        }
        List<Question> levelQs = questions.get(getLevel());

        Question question;
        do {
            int random = new Random().nextInt(levelQs.size());
            question = levelQs.get(random);
        }while (gameStatus.getAnsweredQuestions().contains(question.getId()));

        return question;
    }

    public void increasePoints() {
        int points = gameStatus.getPoints();
        points += getQuestion().getPoints();
        gameStatus.setPoints(points);
    }

//    public void nextLevel(){
//        int level = gameStatus.getLevel();
//        gameStatus.setLevel(++level);
//    }

    public boolean isAnswerCorrect(Question question, String answer) {
        boolean correct = false;
        if(question.isAnswerCorrect(answer)){
            increasePoints();
            gameStatus.addAnsweredQuestion(question.getId());
            correct = true;
        }
        return correct;
    }

}
