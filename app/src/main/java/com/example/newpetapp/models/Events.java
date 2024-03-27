package com.example.newpetapp.models;

import java.io.Serializable;

public class Events implements Serializable {


    String url;
    String title;
    String date;
    String location;
    String details;


    public Events() {
    }

    public Events(String url, String title, String date, String location, String details) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.location = location;
        this.details = details;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
