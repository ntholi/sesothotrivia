package com.nalaneholdings.sesothotrivia.model;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.Progress;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatusHelper;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * This class governs the rules of playing a gameStatus
 * Created by ntholi.nkhatho on 2016/09/12.
 */
public class GamePlayer extends Progress {

    private static final String TAG = "GamePlayer";
    private GameStatus gameStatus;
    private List<Question> questions;
    private String message;
    private int attempts;
    private QuestionLoader questionLoader;


    public GamePlayer(Context context){
        super(context);
        gameStatus = GameStatusHelper.getGameStatus();
        questions = new ArrayList<>();
        if(context instanceof QuestionLoader){
            questionLoader = (QuestionLoader) context;
        }
        showProgressDialog();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("questions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question question = dataSnapshot.getValue(Question.class);
                questions.add(question);
                Log.d(TAG, "Adding question, "+ question.getQuestion());

                if(questions.size() > gameStatus.getLevel()){
                    hideProgressDialog();
                    if(questionLoader != null)
                        questionLoader.onQuestionLoaded();
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
     * a class that implements this interface will only access only when they are fully downloaded
     * from the fire base
     */
    public interface QuestionLoader{
        public void onQuestionLoaded();
    }

    public GameStatus getGameStatus(){return gameStatus;}

    public String getMessage() {
        return message;
    }

    public Question getQuestion(){
        System.out.println("xxx question at level: "+ gameStatus.getLevel());
        return questions.get(gameStatus.getLevel());
    }

    public void increasePoints() {
        int points = gameStatus.getPoints();
        points += getQuestion().getPoints();
        gameStatus.setPoints(points);
    }

    public boolean nextLevel(){
        int level = gameStatus.getLevel();
        gameStatus.setLevel(++level);
        return true;
    }

    public boolean isAnswerCorrect(Question question, String answer) {
        boolean correct = false;
        if(question.isAnswerCorrect(answer)){
            increasePoints();
            correct = true;
        }
//        else decreasePoints();

        return correct;
    }

}
