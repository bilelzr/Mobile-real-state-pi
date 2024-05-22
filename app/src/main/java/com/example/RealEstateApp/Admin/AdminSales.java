package com.example.RealEstateApp.Admin;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.R;
import com.example.RealEstateApp.RealStateDatabase;
import com.example.RealEstateApp.models.Sales;

import java.util.ArrayList;

public class AdminSales extends AppCompatActivity {

    ListView lv;
    com.example.RealEstateApp.Sales pa;
    RealStateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        lv = findViewById(R.id.lv_appointments);

        db = new RealStateDatabase(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sales");


        ArrayList<Sales> Sales = new ArrayList<>();
        Sales = db.getAllSales();
        pa = new com.example.RealEstateApp.Sales(Sales, this);
        pa.notifyDataSetChanged();
        lv.setAdapter(pa);

    }
}