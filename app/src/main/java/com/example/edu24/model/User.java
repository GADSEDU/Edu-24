package com.example.edu24.model;

public class User {
    private String user_id;
    private String user_name;
    private String user_gender;
    private String user_dob;
    private String user_country;
    private String user_phone;
    private String user_email;
    private String[] user_classes;
    private String[] important_messages;

    public User() {}

    public User(String user_name, String user_gender, String user_dob, String user_country, String user_phone, String user_email,
                String[] user_classes, String[] important_messages) {
        this.setUser_name(user_name);
        this.setUser_gender(user_gender);
        this.setUser_dob(user_dob);
        this.setUser_country(user_country);
        this.setUser_phone(user_phone);
        this.setUser_email(user_email);
        this.setUser_classes(user_classes);
        this.setImportant_messages(important_messages);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_country() {
        return user_country;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String[] getUser_classes() {
        return user_classes;
    }

    public void setUser_classes(String[] user_classes) {
        this.user_classes = user_classes;
    }

    public String[] getImportant_messages() {
        return important_messages;
    }

    public void setImportant_messages(String[] important_messages) {
        this.important_messages = important_messages;
    }
}
