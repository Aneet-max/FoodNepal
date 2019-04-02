package com.gaurav.foodnepal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.foodnepal.model.UserReview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RateAndReview extends AppCompatActivity {

    private String placeName;
    private float rating;
    private RatingBar ratingBar;
    private String userEmail;
    private TextView userEmailTV;
    private EditText reviewEditText;
    private Button postButton;
    private DatabaseReference databaseReference;
    private String placeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_and_review);

        ratingBar = findViewById(R.id.ratingBarUserReview);
        userEmailTV = findViewById(R.id.userNameTextView);
        reviewEditText = findViewById(R.id.userReviewEditText);
        postButton = findViewById(R.id.postButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("reviews");


        placeId = getIntent().getExtras().getString("placeId");
        placeName = getIntent().getExtras().getString("placeName");
        rating = getIntent().getExtras().getFloat("rating");

        SharedPreferences pref = getSharedPreferences("SCTPref", MODE_PRIVATE);
        userEmail = pref.getString("email", "");

        ratingBar.setRating(rating);

        userEmailTV.setText("Feddback will be posted as - " + userEmail);


        getSupportActionBar().setTitle(placeName);


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = reviewEditText.getText().toString();

                Float rating = ratingBar.getRating();

                String[] userEmailSplit = userEmail.split("@");
                String userName = userEmailSplit[0];

                if (!TextUtils.isEmpty(review)) {
                    String id = databaseReference.push().getKey();

                    UserReview userReview = new UserReview(review, userName, rating);

                    databaseReference.child(placeId).child(id).setValue(userReview);

                    Toast.makeText(getApplicationContext(), "Thank you for submitting review.", Toast.LENGTH_LONG).show();

                    onBackPressed();

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a review", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
