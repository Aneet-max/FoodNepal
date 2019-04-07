package com.gaurav.foodnepal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaurav.foodnepal.model.Feedbacks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    private EditText nameET;
    private EditText emailET;
    private EditText feedbackET;
    private Button submitBtn;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        nameET = findViewById(R.id.feedbackName);
        emailET = findViewById(R.id.feedbackEmail);
        feedbackET = findViewById(R.id.feedbackET);

        submitBtn = findViewById(R.id.submitBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference("reviews");


        SharedPreferences pref = getSharedPreferences("SCTPref", MODE_PRIVATE);
        String userEmail = pref.getString("email", "");

        emailET.setText(userEmail);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String feedback = feedbackET.getText().toString();

                if (name != "" && email != "" && feedback != "") {
                    String id = databaseReference.push().getKey();

                    Feedbacks userReview = new Feedbacks(name, email, feedback);

                    databaseReference.child(id).setValue(userReview);

                    Toast.makeText(getApplicationContext(), "Thank you for submitting feedback.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
