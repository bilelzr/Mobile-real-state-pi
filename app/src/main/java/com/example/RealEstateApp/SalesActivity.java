package com.example.RealEstateApp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {

    ListView lv;
    com.example.RealEstateApp.Sales pa;
    RealStateDatabase db;
    SharedPreferences shp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        lv = findViewById(R.id.lv_appointments);

        db = new RealStateDatabase(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sales");
        shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);

        int user_id = shp_id.getInt("user_id", 0);

        ArrayList<com.example.RealEstateApp.models.Sales> Sales = new ArrayList<>();
        Sales = db.getAllSalesByUser(user_id);
        pa = new com.example.RealEstateApp.Sales(Sales, this);
        pa.notifyDataSetChanged();
        lv.setAdapter(pa);

    }
}