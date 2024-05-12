package com.example.shoppingapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    ListView lv;
    AppointmentAdapter pa;
    RealStateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        lv = findViewById(R.id.lv_appointments);

        db = new RealStateDatabase(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Appointments");

        ArrayList<Property> p = new ArrayList<>();
        p = db.getAllProductsInPurchases();
        pa = new AppointmentAdapter(p,this);
        pa.notifyDataSetChanged();
        lv.setAdapter(pa);

    }
}