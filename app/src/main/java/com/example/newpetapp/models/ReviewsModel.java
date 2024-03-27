package com.example.newpetapp.models;

import java.io.Serializable;

public class ReviewsModel implements Serializable {
    String comments,userId,image,name;
    float ratings;

    public ReviewsModel(String comments, String userId, String image,String name,float ratings) {
        this.comments = comments;
        this.userId = userId;
        this.image = image;
        this.name=name;
        this.ratings=ratings;
    }


    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReviewsModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
