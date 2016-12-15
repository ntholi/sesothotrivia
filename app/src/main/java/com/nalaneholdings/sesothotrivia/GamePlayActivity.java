package com.nalaneholdings.sesothotrivia;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.model.AdvertFactory;
import com.nalaneholdings.sesothotrivia.model.GamePlayer;
import com.nalaneholdings.sesothotrivia.model.bean.Advert;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;
import com.nalaneholdings.sesothotrivia.model.bean.Player;
import com.nalaneholdings.sesothotrivia.model.bean.PlayerFactory;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.Calendar;
import java.util.Date;

public class GamePlayActivity extends AppCompatActivity implements GamePlayer.QuestionLoadable {

    private GamePlayer game;
    private Question question;
    private FirebaseUser user;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        game = new GamePlayer(this);

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(user != null ? getFirstName(user) : "unregistered");
        Typeface font_stan = Typeface.createFromAsset(getAssets(), "fonts/STAN0755.TTF");
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setTypeface(font_stan);

//        int time = question.getTime();
//        if (time > 0) {
//            new CountDownTimer(time * 100, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                }
//                @Override
//                public void onFinish() {
//
//                }
//
//            }.start();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Player player = PlayerFactory.getPlayer();
        player.applyReverseRating();
        player.setLastPlayedAt(new Date().toString());
        database.child(Player.NAME).child(user.getUid()).setValue(player);

    }

    private String getFirstName(FirebaseUser user) {
        String name = user.getDisplayName();
        return (name != null ? name.split(" ")[0] : null);
    }

    public void loadQuestion() {
        question = game.getQuestion();
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
        transaction.commit();
    }

    public void answerSelected(View view) {
        Handler delayHandler = new Handler();
        if (game.isAnswerCorrect(question, extractAnswer(view))) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
            mp.start();
            TextView questionView = (TextView) findViewById(R.id.question_view);
            questionView.setEnabled(false);
            TextView questionStatus = (TextView) findViewById(R.id.question_status);
            questionStatus.requestFocus();
            questionStatus.setFocusableInTouchMode(true);
            questionStatus.setText(R.string.correct_answer_label);
            delayHandler.postDelayed(new NextQuestionLoader(view), 2000);
        } else {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.incorrect);
            mp.start();
            Button button = (Button) view;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_wrong_answer));
                button.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
            else {
                button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_wrong_answer));
            }
            delayHandler.postDelayed(new NextQuestionLoader(view), 1000);
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
        String score = String.format("%04d", game.getGameStatus().getPoints());
        scoreView.setText(score);

        LinearLayout layout = (LinearLayout) findViewById(R.id.level);
        layout.removeAllViews();
        for (int i = 0; i < game.getLevel(); i++){
            ImageView image = new ImageView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(40, ViewGroup.LayoutParams.WRAP_CONTENT);
            image.setLayoutParams(params);
            image.setImageResource(R.drawable.thebe_br);
            layout.addView(image);
        }
    }

    private void loadAdvert(){
        TextView advertView = (TextView) findViewById(R.id.advert);
        Advert advert = AdvertFactory.getAdvert();
        advertView.setText(advert.getBody());

    }

    @Override
    public void onQuestionLoaded() {
        loadQuestion();
        displayScore();
        loadAdvert();
    }

    private class NextQuestionLoader implements Runnable {
        private View view;

        NextQuestionLoader(View view) {
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

            try{
                loadQuestion();
            }catch (IndexOutOfBoundsException ex){
                Snackbar.make(view.getRootView(), "No more questions, "+ex.getMessage(),
                        Snackbar.LENGTH_LONG).show();
            }
            displayScore();
            loadAdvert();
        }
    }
}
