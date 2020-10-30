package com.example.edu24.model;

public class ModelClass {
    private String className;
    private String section;
    private String student;

    public ModelClass(String className, String section, String student) {
        this.className = className;
        this.section = section;
        this.student = student;
    }

    public String getClassName() {
        return className;
    }

    public String getSection() {
        return section;
    }

    public String getStudent() {
        return student;
    }
}
