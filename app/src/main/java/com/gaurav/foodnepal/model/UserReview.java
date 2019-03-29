package com.gaurav.foodnepal.model;

public class UserReview {

    String review;
    String userName;

    public UserReview() {
    }

    public UserReview(String review, String userName) {
        this.review = review;
        this.userName = userName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "review='" + review + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
