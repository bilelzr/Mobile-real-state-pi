package com.example.shoppingapp;

import android.content.Context;

public class Property {

    private int id;
    private int image;
    private String name;
    private double price;
    private String location;
    private String description;

    private String type;
    private double discount;

    public Property(int image, String name, double price, String location, String description, double discount,String type ) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
        this.description = description;
        this.type = type;
        this.discount = discount;
    }

    public Property(int image, String name, double price, String location, String type) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
        this.type = type;
    }

    public Property(int image, String name , double price , String location ){
        this.image=image;
        this.name=name;
        this.price=price;
        this.location= location;
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



    public Property(int id, int image, String name, double price, String brand,  String description, double discount) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = brand;
        this.description = description;
        this.discount = discount;
    }








    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
