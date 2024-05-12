package com.example.RealEstateApp.models;

public class Appointment {
    private Property property;
    private String Date;

    private int user_fk;


    public Appointment(Property property, String date, int user_fk) {
        this.property = property;
        Date = date;
        this.user_fk = user_fk;
    }

    public Appointment(Property property, String date) {
        this.property = property;
        Date = date;
    }

    public int getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(int user_fk) {
        this.user_fk = user_fk;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
