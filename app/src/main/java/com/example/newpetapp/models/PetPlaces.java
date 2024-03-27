package com.example.newpetapp.models;

import java.io.Serializable;

public class PetPlaces implements Serializable {
    String url;
    String name;
    String location;


    public PetPlaces() {
    }

    public PetPlaces(String url, String name, String location) {
        this.url = url;
        this.name = name;
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
