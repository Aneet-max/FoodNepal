package com.gaurav.foodnepal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn, btnSignUp;
    CheckBox saveLoginCheckBox;
    String phone = "9846624500";
    String password = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entredPhn = edtPhone.getText().toString().trim();
                String entredPswd = edtPassword.getText().toString().trim();
                if (entredPhn.equals(phone) && entredPswd.equals(password)) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    edtPassword.startAnimation(shake);
                    edtPassword.setError("Please enter a valid phone/password");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edtPassword.setError(null);
                            edtPassword.setText("");
                        }
                    }, 1500);

                }
            }
        });

    }
}
