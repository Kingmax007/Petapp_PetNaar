package com.example.newpetapp.models;

import java.io.Serializable;
import java.util.List;

public class ProfilePets implements Serializable {
    String key;
    String image;
    String name;
    String type;
    String age;
    String breed;
    String interest;

    public List<ReviewsModel> getReviewsModelsList() {
        return reviewsModelsList;
    }

    List<ReviewsModel>reviewsModelsList;


    public ProfilePets() {
    }


    public ProfilePets(String image, String name, String type, String age, String breed, String interest,List<ReviewsModel>reviewsModelsList) {
        this.image = image;
        this.name = name;
        this.type = type;
        this.age = age;
        this.breed = breed;
        this.interest = interest;
        this.reviewsModelsList=reviewsModelsList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setReviewsModelsList(List<ReviewsModel> reviewsModelsList) {
        this.reviewsModelsList = reviewsModelsList;
    }
}

