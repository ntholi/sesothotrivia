package com.nalaneholdings.sesothotrivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GamePlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView username = (TextView) findViewById(R.id.username);
        username.setText(user != null ? user.getDisplayName() : "unregistered");
    }
}
