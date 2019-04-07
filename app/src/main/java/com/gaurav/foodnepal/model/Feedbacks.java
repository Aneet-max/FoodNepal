package com.gaurav.foodnepal.model;

public class Feedbacks {
    String name;
    String email;
    String feedbackText;


    public Feedbacks(String name, String email, String feedbackText) {
        this.name = name;
        this.email = email;
        this.feedbackText = feedbackText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    @Override
    public String toString() {
        return "Feedbacks{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", feedbackText='" + feedbackText + '\'' +
                '}';
    }
}
