package com.example.jobportalapp.model;

public class Data {

    String title;
    String description;
    String skills;
    String Salary;

    String id;
    String date;

    private Data(){

    }

    public Data(String title, String description, String skills, String salary, String id, String date) {
        this.title = title;
        this.description = description;
        this.skills = skills;
        Salary = salary;
        this.id = id;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
