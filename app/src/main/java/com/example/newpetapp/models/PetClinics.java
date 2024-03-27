package com.example.newpetapp.models;

import java.io.Serializable;

public class PetClinics implements Serializable {
    String url;
    String name;
    String address;
    String number;

    public PetClinics() {
    }

    public PetClinics(String url, String name, String address, String number) {
        this.url = url;
        this.name = name;
        this.address = address;
        this.number = number;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
