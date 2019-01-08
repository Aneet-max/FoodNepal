package com.gaurav.foodnepal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    private final int interval = 4000; // 4 Second
    private RelativeLayout splashRL;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            Intent intent = new Intent(SplashActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashRL = findViewById(R.id.splashRL);
        Animation popUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_up);
        splashRL.startAnimation(popUp);


        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }
}
