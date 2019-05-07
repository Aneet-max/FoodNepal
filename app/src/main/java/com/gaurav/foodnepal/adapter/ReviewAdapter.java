package com.gaurav.foodnepal.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.foodnepal.R;
import com.gaurav.foodnepal.model.UserReview;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<UserReview> {
    private Activity context;
    private List<UserReview> userReviewList;
    private DatabaseReference databaseReference;
    private String placeId;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    public ReviewAdapter(Activity context, List<UserReview> userReviewList,
                         DatabaseReference databaseReference, String placeId, SharedPreferences.Editor editor, SharedPreferences pref) {
        super(context, R.layout.review_list, userReviewList);
        this.context = context;
        this.userReviewList = userReviewList;
        this.databaseReference = databaseReference;
        this.placeId = placeId;
        this.editor = editor;
        this.pref = pref;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.review_list, null, true);

        TextView userReviewTextView = listViewItem.findViewById(R.id.userReviewTextView);

        TextView userNameTextView = listViewItem.findViewById(R.id.userNameTextView);

        RatingBar ratingBar = listViewItem.findViewById(R.id.ratingBar);

        TextView voteTextView = listViewItem.findViewById(R.id.upvoteCount);

        final ImageView upvote = listViewItem.findViewById(R.id.upvote);
        final ImageView downvote = listViewItem.findViewById(R.id.downvote);

        final UserReview userReview = userReviewList.get(position);
        String userEmail = userReview.getUserName();
        String[] userEmailSplit = userEmail.split("@");
        String userName = userEmailSplit[0];

        userReviewTextView.setText(userReview.getReview());

        userNameTextView.setText("- " + userName);

        ratingBar.setRating(userReview.getRating());

        int vote = 0;
        vote = userReview.getVote();

        voteTextView.setText(String.valueOf(vote));


        /**
         * Upvote and downvote functionality
         */

        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUpvoted = pref.getBoolean("upvoted", false);
                if (isUpvoted) {
                    Toast.makeText(context, "You have already upvoted", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("upvoted", true);
                    editor.putBoolean("downvoted", false);
                    editor.apply();
                    int vote = userReview.getVote();
                    vote += 1;

                    databaseReference.child(placeId).child(userReview.getId()).child("vote").setValue(vote);
                }


            }
        });
        downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDownvoted = pref.getBoolean("downvoted", false);
                if (isDownvoted) {
                    Toast.makeText(context, "You have already down voted", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("downvoted", true);
                    editor.putBoolean("upvoted", false);
                    editor.apply();
                    int vote = userReview.getVote();
                    vote -= 1;

                    databaseReference.child(placeId).child(userReview.getId()).child("vote").setValue(vote);
                }

            }
        });

        return listViewItem;
    }
}
