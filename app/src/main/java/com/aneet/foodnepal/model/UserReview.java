package com.aneet.foodnepal.model;

public class UserReview {

    String id;
    String review;
    String userName;
    Float rating;
    Integer vote;

    public UserReview() {
    }


    public UserReview(String id, String review, String userName, Float rating, Integer vote) {
        this.id = id;
        this.review = review;
        this.userName = userName;
        this.rating = rating;
        this.vote = vote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "id='" + id + '\'' +
                ", review='" + review + '\'' +
                ", userName='" + userName + '\'' +
                ", rating=" + rating +
                ", vote=" + vote +
                '}';
    }
}
