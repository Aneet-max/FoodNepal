package com.aneet.foodnepal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aneet.foodnepal.utility.Utility;

public class SplashActivity extends AppCompatActivity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private RelativeLayout splashRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashRL = findViewById(R.id.splashRL);
        Animation popUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_up);
        splashRL.startAnimation(popUp);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences pref = getSharedPreferences("SCTPref", MODE_PRIVATE);
                boolean isUserRegistred = pref.getBoolean("userRegistered", false);
                Log.i("TAG", "run: isUserRegister: " + isUserRegistred);
                if (isUserRegistred) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent(SplashActivity.this, Start.class);
                    startActivity(intent);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        boolean isDeviceOnline = Utility.isDeviceOnline(getApplicationContext());
        if (isDeviceOnline) {
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
}
