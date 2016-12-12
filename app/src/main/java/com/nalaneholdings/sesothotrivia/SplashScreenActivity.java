package com.nalaneholdings.sesothotrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nalaneholdings.sesothotrivia.model.bean.PlayerFactory;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity implements PlayerFactory.PlayerLoadable {


    private final PlayerFactory.PlayerLoadable instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null){
                        PlayerFactory.initialize(instance);
                    }
                    else {
                        sleep(3000);
                        callNextActivity();
                    }
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timerThread.start();
    }

    private void callNextActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onPlayerLoaded() {
        callNextActivity();
    }
}
