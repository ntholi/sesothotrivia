package com.nalaneholdings.sesothotrivia;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user == null) {
            loadLogInView();
        }
        loadButtons();

    }

    private void loadButtons() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/SHOWG.TTF");
        Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setTypeface(custom_font);
        Button scoresButton = (Button) findViewById(R.id.scores_button);
        scoresButton.setTypeface(custom_font);
        Button referencesButton = (Button) findViewById(R.id.references_button);
        referencesButton.setTypeface(custom_font);
        Button creditsButton = (Button) findViewById(R.id.credits_button);
        creditsButton.setTypeface(custom_font);
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onPlay(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        startActivity(intent);
    }

    public void onScores(View view) {
        Intent intent = new Intent(this, ScoreBoardActivity.class);
        startActivity(intent);
    }
}
