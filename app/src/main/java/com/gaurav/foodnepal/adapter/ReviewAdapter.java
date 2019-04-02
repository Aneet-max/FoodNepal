package com.gaurav.foodnepal.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gaurav.foodnepal.R;
import com.gaurav.foodnepal.model.UserReview;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<UserReview> {
    private Activity context;
    private List<UserReview> userReviewList;

    public ReviewAdapter(Activity context, List<UserReview> userReviewList) {
        super(context, R.layout.review_list, userReviewList);
        this.context = context;
        this.userReviewList = userReviewList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.review_list, null, true);

        TextView userReviewTextView = listViewItem.findViewById(R.id.userReviewTextView);

        TextView userNameTextView = listViewItem.findViewById(R.id.userNameTextView);

        RatingBar ratingBar = listViewItem.findViewById(R.id.ratingBar);

        UserReview userReview = userReviewList.get(position);
        String userEmail = userReview.getUserName();
        String[] userEmailSplit = userEmail.split("@");
        String userName = userEmailSplit[0];

        userReviewTextView.setText(userReview.getReview());

        userNameTextView.setText("- " + userName);

        ratingBar.setRating(userReview.getRating());

        return listViewItem;
    }
}
