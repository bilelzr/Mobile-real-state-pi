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
import com.example.RealEstateApp.models.Sales;

import java.util.ArrayList;

public class SalesAdminAdapter extends BaseAdapter {

    ArrayList<Sales> sales;
    Context context;

    public SalesAdminAdapter(ArrayList<Sales> sales, Context context) {
        this.sales = sales;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sales.size();
    }

    @Override
    public Sales getItem(int i) {
        return sales.get(i);
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
            v = LayoutInflater.from(context).inflate(R.layout.custome_admin_sales, null, false);
        }

        ImageView img = (ImageView) v.findViewById(R.id.img_property_appointment);
        TextView tv_name = v.findViewById(R.id.tv_name_property);
        TextView tv_price = v.findViewById(R.id.tv_price_property);
        TextView tv_type = v.findViewById(R.id.tv_type_property);
        TextView tv_dateApointment = v.findViewById(R.id.tv_date_appointment_property);

        Sales sales = getItem(i);
        if (Integer.parseInt(sales.getProperty().getImage()) != 0) {
            img.setImageResource(Integer.parseInt(sales.getProperty().getImage()));
        } else {
            img.setImageResource(R.drawable.products);
        }
        tv_name.setText(sales.getProperty().getName());
        tv_price.setText(sales.getProperty().getPrice() + "DT");
        tv_type.setText(sales.getProperty().getType());
        tv_dateApointment.setText(sales.getDate());
        return v;
    }
}
