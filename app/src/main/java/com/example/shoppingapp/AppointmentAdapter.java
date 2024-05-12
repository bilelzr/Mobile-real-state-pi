package com.example.shoppingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AppointmentAdapter extends BaseAdapter {

    ArrayList<Property> purchases;
    Context context;

    public AppointmentAdapter(ArrayList<Property> purchases, Context context) {
        this.purchases = purchases;
        this.context = context;
    }

    @Override
    public int getCount() {
        return purchases.size();
    }

    @Override
    public Property getItem(int i) {
        return purchases.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if(v==null){
            v = LayoutInflater.from(context).inflate(R.layout.custome_appointment_products,null,false);
        }

        ImageView img = (ImageView) v.findViewById(R.id.img_property_appointment);
        TextView tv_name = v.findViewById(R.id.tv_name_property);
        TextView tv_price = v.findViewById(R.id.tv_price_purchases);
        TextView tv_brand = v.findViewById(R.id.tv_brand_purchases);
        RatingBar rating = v.findViewById(R.id.rating_purchases);

        Property p = getItem(i);
        if(p.getImage() != 0){
            img.setImageResource(p.getImage());
        }else{
            img.setImageResource(R.drawable.products);
        }
        tv_name.setText(p.getName());
        tv_price.setText(p.getPrice()+"$");
        tv_brand.setText(p.getLocation());

        return v;
    }
}
