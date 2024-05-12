package com.example.RealEstateApp.models;

import android.content.Context;

public class Property {

    private int id;
    private String image;
    private String name;
    private double price;
    private String location;
    private String description;

    private String type;
    private double discount;


    public Property(String image, String name, double price, String location, String description, double discount, String type) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
        this.description = description;
        this.type = type;
        this.discount = discount;
    }

    public Property(String image, String name, double price, String location, String type) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
        this.type = type;
    }

    public Property(String image, String name, double price, String location) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
    }


    public Property(String name, double price, String location, String description, double discount) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.description = description;
        this.discount = discount;
    }

    public Property(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Property(int id, String image, String name, double price, String brand, String description, double discount, String type) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = brand;
        this.description = description;
        this.discount = discount;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public static int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
