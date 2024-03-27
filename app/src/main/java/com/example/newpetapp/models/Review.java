package com.example.newpetapp.models;

public class Review {

    String uid;
    String details;
    float rating;

    public Review(String uid, String details, float rating) {
        this.uid = uid;
        this.details = details;
        this.rating = rating;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
