package com.example.RealEstateApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppointmentAdapter extends BaseAdapter {

    ArrayList<Appointment> appointments;
    Context context;

    public AppointmentAdapter(ArrayList<Appointment> appointments, Context context) {
        this.appointments = appointments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Appointment getItem(int i) {
        return appointments.get(i);
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
        TextView tv_price = v.findViewById(R.id.tv_price_property);
        TextView tv_type = v.findViewById(R.id.tv_type_property);
        TextView tv_dateApointment = v.findViewById(R.id.tv_date_appointment_property);

        Appointment appointment = getItem(i);
        if(appointment.getProperty().getImage() != 0){
            img.setImageResource(appointment.getProperty().getImage());
        }else{
            img.setImageResource(R.drawable.products);
        }
        tv_name.setText(appointment.getProperty().getName());
        tv_price.setText(appointment.getProperty().getPrice()+"DT");
        tv_type.setText(appointment.getProperty().getType());
        tv_dateApointment.setText(appointment.getDate());


        return v;
    }
}
