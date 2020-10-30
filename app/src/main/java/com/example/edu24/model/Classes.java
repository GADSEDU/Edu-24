package com.example.edu24.model;

import java.util.ArrayList;
import java.util.List;

public class Classes {
    private String class_id;
    private String class_teacher;
    private ArrayList<String> class_student;
    private String class_name;
    private String class_status;
    private String class_code;
    private String class_section;
    private String class_Room;
    private String class_subject;

    public Classes() {}

    public Classes(String class_teacher, String class_name, String class_code, String class_section, String class_Room, String class_subject) {
        this.class_name = class_name;
        this.class_code = class_code;
        this.class_section = class_section;
        this.class_Room = class_Room;
        this.class_subject = class_subject;
        this.class_teacher = class_teacher;
    }


    public String getClass_id() {
        return class_id;
    }

    public String getClass_section() {
        return class_section;
    }

    public void setClass_section(String class_section) {
        this.class_section = class_section;
    }

    public String getClass_Room() {
        return class_Room;
    }

    public void setClass_Room(String class_Room) {
        this.class_Room = class_Room;
    }

    public String getClass_subject() {
        return class_subject;
    }

    public void setClass_subject(String class_subject) {
        this.class_subject = class_subject;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_teacher() {
        return class_teacher;
    }

    public void setClass_teacher(String class_teacher) {
        this.class_teacher = class_teacher;
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

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }
}
