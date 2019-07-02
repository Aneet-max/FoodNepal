package com.aneet.foodnepal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aneet.foodnepal.firebase.Login;
import com.aneet.foodnepal.firebase.Register;


public class Start extends AppCompatActivity {

    private Button btnLogin, btnSignup;
    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnClose = findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, Register.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
