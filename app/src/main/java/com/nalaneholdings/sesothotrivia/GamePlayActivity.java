package com.nalaneholdings.sesothotrivia;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        displayScore();
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

    public void answerSelected(View view) {
        if (player.isAnswerCorrect(question, extractAnswer(view))) {
            TextView questionView = (TextView) findViewById(R.id.question_view);
            assert questionView != null;
            questionView.setEnabled(false);

            TextView questionStatus = (TextView) findViewById(R.id.question_status);
            assert questionStatus != null;
            questionStatus.requestFocus();
            questionStatus.setFocusableInTouchMode(true);
            questionStatus.setText(R.string.correct_answer_label);
            if(player.nextLevel()){
                Handler delayHandler = new Handler();
                delayHandler.postDelayed(new NextQuestionLoader(view), 2000);
            }
            else{
                Snackbar.make(view.getRootView(), player.getMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(view.getRootView(), R.string.wrong_answer_warning,
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private String extractAnswer(View view) {
        CharSequence answer = "";
        View rootView = view.getRootView();
        Button answer1 = (Button) rootView.findViewById(R.id.answer_1);
        Button answer2 = (Button) rootView.findViewById(R.id.answer_2);
        Button answer3 = (Button) rootView.findViewById(R.id.answer_3);
        Button yes = (Button) rootView.findViewById(R.id.yes);
        Button no = (Button) rootView.findViewById(R.id.no);

        if (answer1 != null
                && view.getId() == answer1.getId()) {
            answer = answer1.getText();
        } else if (answer2 != null
                && view.getId() == answer2.getId()) {
            answer = answer2.getText();
        } else if (answer3 != null
                && view.getId() == answer3.getId()) {
            answer = answer3.getText();
        } else if (yes != null
                && view.getId() == yes.getId()) {
            answer = yes.getText();
        } else if (no != null
                && view.getId() == no.getId()) {
            answer = no.getText();
        }
        return answer.toString();
    }

    private void displayScore() {
        TextView scoreView = (TextView) findViewById(R.id.score);
        String score = String.format("%04d", player.getGameStatus().getLevel());
        scoreView.setText(score);
    }

    @Override
    public void onQuestionLoaded() {
        loadQuestion();
        displayScore();
    }

    private class NextQuestionLoader implements Runnable {
        private View view;

        public NextQuestionLoader(View view) {
            this.view = view;
        }

        @Override
        public void run() {
            TextView questionView = (TextView) findViewById(R.id.question_view);
            assert questionView != null;
            questionView.setEnabled(true);
            TextView questionStatus = (TextView) findViewById(R.id.question_status);
            assert questionStatus != null;
            questionStatus.setText("");

            loadQuestion();
            displayScore();
        }
    }
}
