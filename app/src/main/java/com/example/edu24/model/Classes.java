package com.example.edu24.model;

public class Classes {
    private String class_id;
    private String class_creator;
    private String[] class_members;
    private String class_name;
    private String class_status;

    public Classes() {}

    public Classes(String class_creator, String[] class_members, String class_name, String class_status) {
        this.setClass_creator(class_creator);
        this.setClass_members(class_members);
        this.setClass_name(class_name);
        this.setClass_status(class_status);
    }


    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_creator() {
        return class_creator;
    }

    public void setClass_creator(String class_creator) {
        this.class_creator = class_creator;
    }

    public String[] getClass_members() {
        return class_members;
    }

    public void setClass_members(String[] class_members) {
        this.class_members = class_members;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_status() {
        return class_status;
    }

    public void setClass_status(String class_status) {
        this.class_status = class_status;
    }
}
