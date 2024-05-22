package com.example.RealEstateApp.models;

public class Sales {

    private int id;
    private Property property;
    private String Date;

    private int user_fk;

    private String payementMethod;

    private String checkNumber;

    private float commission;

    private String status;


    public Sales(Property property, String date, int user_fk) {
        this.property = property;
        Date = date;
        this.user_fk = user_fk;
    }

    public Sales(Property property, String date, int user_fk, String payementMethod, String checkNumber) {
        this.property = property;
        Date = date;
        this.user_fk = user_fk;
        this.payementMethod = payementMethod;
        this.checkNumber = checkNumber;
    }

    public Sales(Property property, String date) {
        this.property = property;
        Date = date;
    }

    public Sales(Property property, String date, String payementMethod, String checkNumber) {
        this.property = property;
        Date = date;
        this.checkNumber = checkNumber;
        this.payementMethod = payementMethod;
    }

    public Sales(Property property, String date, String payementMethod, float commission) {
        this.property = property;
        Date = date;
        this.commission = commission;
        this.payementMethod = payementMethod;
    }

    public Sales(int id, Property property, String date, String payementMethod, float commission, String status) {
        this.id = id;
        this.property = property;
        Date = date;
        this.commission = commission;
        this.payementMethod = payementMethod;
        this.status = status;
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

    public String getPayementMethod() {
        return payementMethod;
    }

    public void setPayementMethod(String payementMethod) {
        this.payementMethod = payementMethod;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }


    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
