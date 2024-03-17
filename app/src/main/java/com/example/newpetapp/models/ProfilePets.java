package com.example.newpetapp.models;

public class ProfilePets {
    int image;
    String name;
    String details;
    int image2;


    public ProfilePets(int image, String name, String details, int image2) {
        this.image = image;
        this.name = name;
        this.details = details;
        this.image2 = image2;
    }


    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
