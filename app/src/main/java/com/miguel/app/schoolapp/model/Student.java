package com.miguel.app.schoolapp.model;

public class Student {
    private String name;
    private String lastName;
    private String date;

    public Student(String name, String lastName, String date) {
        setName(name);
        setLastName(lastName);
        setDate(date);
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Student setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Student setDate(String date) {
        this.date = date;
        return this;
    }

    public String fullName() {
        return getName() + " " + getLastName();
    }
}
