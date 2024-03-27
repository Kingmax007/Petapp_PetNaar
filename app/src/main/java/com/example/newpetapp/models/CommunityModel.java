package com.example.newpetapp.models;

import java.util.List;

public class CommunityModel {
    String Uid,image,postUserName;
    List<ProfilePets>list;

    String key;

    public String getPostUserName() {
        return postUserName;
    }
    public CommunityModel(){

    }

    public CommunityModel(String uid, String image, String postUserName, List<ProfilePets> list) {
        Uid = uid;
        this.image = image;
        this.list = list;
        this.postUserName=postUserName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return Uid;
    }

    public String getImage() {
        return image;
    }

    public List<ProfilePets> getList() {
        return list;
    }
}
