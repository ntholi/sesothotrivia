package com.nalaneholdings.sesothotrivia;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nalaneholdings.sesothotrivia.model.GamePlayer;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

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
        username.setText(user != null ? getFirstName(user) : "unregistered");
        Typeface font_stan = Typeface.createFromAsset(getAssets(), "fonts/STAN0755.TTF");
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setTypeface(font_stan);
        String score = String.format("%04d", player.getGameStatus().getLevel());
        scoreView.setText(score);
    }

    private String getFirstName(FirebaseUser user) {
        String name = user.getDisplayName();
        return (name != null ? name.split(" ")[0] : null);
    }

    public void loadQuestion() {
        question = player.getQuestion();
        Typeface font_school = Typeface.createFromAsset(getAssets(), "fonts/Schoolbell.ttf");
        TextView questionView = (TextView) findViewById(R.id.question_view);
        questionView.setTypeface(font_school);
        questionView.setText(question.getQuestion());

        if (question.isMultipleChoice()) {
            ThreeAnswersFragment fragment = new ThreeAnswersFragment();
            loadQuestionFragmentByType(fragment);
        } else {
            TwoAnswersFragment fragment = new TwoAnswersFragment();
            loadQuestionFragmentByType(fragment);
        }
        Typeface font_mom = Typeface.createFromAsset(getAssets(), "fonts/MomÂ«t__.ttf");
        TextView hintTitle = (TextView) findViewById(R.id.hint_title);
        hintTitle.setTypeface(font_mom);

        Typeface font_courbd = Typeface.createFromAsset(getAssets(), "fonts/courbd.ttf");
        TextView hint = (TextView) findViewById(R.id.hint);
        hint.setTypeface(font_courbd);
        hint.setText(question.getHint());
    }

    private void loadQuestionFragmentByType(AnswerFragment fragment) {
        fragment.setPossibleAnswers(question);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.question_type_fragment, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onQuestionLoaded() {
        loadQuestion();
    }
}
