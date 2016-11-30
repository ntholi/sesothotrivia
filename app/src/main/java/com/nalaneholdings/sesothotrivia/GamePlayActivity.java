package com.nalaneholdings.sesothotrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.model.GamePlayer;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity implements GamePlayer.QuestionLoader{


    private GamePlayer player;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        player = new GamePlayer(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView username = (TextView) findViewById(R.id.username);
        username.setText(user != null ? user.getDisplayName() : "unregistered");
        TextView scoreView = (TextView) findViewById(R.id.score);
        String score = String.valueOf(player.getGameStatus().getLevel());
        scoreView.setText(score);
    }

    public void loadQuestion() {
        question = player.getQuestion();
        TextView questionView = (TextView) findViewById(R.id.question_view);
        assert questionView != null;
        questionView.setText(question.getQuestion());

        if (question.isMultipleChoice()) {
            TwoQuestionsFragment fragment = new TwoQuestionsFragment();
            loadQuestionFragmentByType(fragment);
        } else {
            FourQuestionsFragment fragment = new FourQuestionsFragment();
            loadQuestionFragmentByType(fragment);
        }
    }

    private void loadQuestionFragmentByType(QuestionFragment fragment) {
    }

    @Override
    public void onQuestionLoaded() {
        loadQuestion();
    }
}
