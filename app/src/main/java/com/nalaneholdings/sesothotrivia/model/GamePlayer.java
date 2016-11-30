package com.nalaneholdings.sesothotrivia.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.R;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * This class governs the rules of playing a gameStatus
 * Created by ntholi.nkhatho on 2016/09/12.
 */
public class GamePlayer {

    private static final String TAG = "GamePlayer";
    private GameStatus gameStatus;
    private List<Question> questions = new ArrayList<>();
    private String message;
    private Context context;
    private int attempts;
    private QuestionLoader questionLoader;


    public GamePlayer(Context context){
        this.context = context;
        this.gameStatus = new GameStatus();
        if(context instanceof QuestionLoader){
            questionLoader = (QuestionLoader) context;
            System.out.println("***************************************************************");
        }
        showProgressDialog();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("questions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question question = dataSnapshot.getValue(Question.class);
                questions.add(question);
                Log.d(TAG, "Adding question, "+ question.getQuestion());

                if(questions.size() >= gameStatus.getLevel()){
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


    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
        return questions.get(gameStatus.getLevel());
    }

    /**
     *
     * @return returns true if level has been incremented
     */
    public boolean increaseLevel() {
        boolean incremented = false;
        int level = gameStatus.getLevel();
        int points = gameStatus.getPoints();

        if(level == 1 && points >= 20){
            ++level;
            incremented = true;
        }
        else if(level >= 2 && points >= 30){
            ++level;
            incremented = true;
        }

        gameStatus.setPoints(points);
        return incremented;
    }

    /**
     * Decrease points based on question attempts, the number of times the user attempted to answer
     * the question
     */
    public void decreasePoints(){
        int level = gameStatus.getLevel();
        int points = gameStatus.getPoints();

        if(attempts >= 2 && level <= 5){
            points -= 3; // TODO: 2016/09/12 Are you sure it doesn't cover level 6 to 10?
        }
        else if(level >= 11 && level <= 19){
            points -= 4;
        }

        gameStatus.setPoints(points);
    }

    public void increasePoints() {
        int level = gameStatus.getLevel();
        int points = gameStatus.getPoints();

        if(level == 1){
            points += 5;
        }
        else if(level >= 2 && level <= 4){
            points += 6;
        }
        else if(level >= 5 && level <= 9){
            points += 8;
        }
        else if(level >= 10 && level <= 19){
            points += 3;
        }
        else if(level >= 20){
            points += 1;
        }
        gameStatus.setPoints(points);
    }

    public boolean nextLevel(){
        boolean proceed = false;
        int level = gameStatus.getLevel();
        int points = gameStatus.getPoints();

        if(level == 1 && points >= 20){
            proceed = true;
        }else message = context.getString(R.string.insufficient_points, 20);

        if(level == 2 && points >= 30){
            proceed = true;
        } else message = context.getString(R.string.insufficient_points, 30);

        return proceed;
    }

    public boolean isAnswerCorrect(Question question, String answer) {
        boolean correct = false;
        if(question.isAnswerCorrect(answer)){
           increasePoints();
            correct = true;
        }
        else decreasePoints();

        return correct;
    }

}
