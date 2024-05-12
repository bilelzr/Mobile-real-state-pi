package com.example.RealEstateApp.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.RealEstateApp.R;
import com.example.RealEstateApp.models.Property;

import java.util.ArrayList;

public class PropertyListAdminAdapter extends BaseAdapter {

    ArrayList<Property> properties;
    Context context;

    public PropertyListAdminAdapter(ArrayList<Property> properties, Context context) {
        this.properties = properties;
        this.context = context;
    }

    @Override
    public int getCount() {
        return properties.size();
    }

    @Override
    public Property getItem(int i) {
        return properties.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.custom_prodcut_display, null, false);
        }

        ImageView img = (ImageView) v.findViewById(R.id.img_property_appointment);
        TextView tv_name = v.findViewById(R.id.tv_name_property);
        TextView tv_price = v.findViewById(R.id.tv_price_property);
        TextView tv_type = v.findViewById(R.id.tv_type_property);
        TextView tv_location = v.findViewById(R.id.tv_location_property);
        ImageView imgDelete = (ImageView) v.findViewById(R.id.img_delete);
        imgDelete.setImageResource(R.drawable.delete);
        Property property = getItem(i);
        if (Integer.parseInt(property.getImage()) != 0) {
            img.setImageResource(Integer.parseInt(property.getImage()));
        } else {
            img.setImageResource(R.drawable.products);
        }
        tv_name.setText(property.getName());
        tv_price.setText(property.getPrice() + "DT");
        tv_type.setText(property.getType());
        tv_location.setText(property.getLocation());
        return v;
    }
}