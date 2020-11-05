package com.example.edu24.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String user_id;
    private String user_first_name;
    private String user_surname;
    private String user_email;
    private String profile_image;
    private HashMap<String,String> user_classes;

    public User() {}

    public User(String user_id, String user_first_name, String user_surname, String user_email, String profile_image) {
        this.user_id = user_id;
        this.user_first_name = user_first_name;
        this.user_surname = user_surname;
        this.user_email = user_email;
        this.profile_image = profile_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public HashMap<String,String> getUser_classes() {
        return user_classes;
    }

    public void setUser_classes(HashMap<String,String> user_classes) {
        this.user_classes = user_classes;
    }
}
