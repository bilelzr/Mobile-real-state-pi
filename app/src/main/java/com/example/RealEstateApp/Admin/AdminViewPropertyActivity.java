package com.example.RealEstateApp.Admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.R;
import com.example.RealEstateApp.RealStateDatabase;
import com.example.RealEstateApp.models.Property;

import java.util.ArrayList;

public class AdminViewPropertyActivity extends AppCompatActivity {


    ListView lv;
    PropertyListAdminAdapter pa;
    RealStateDatabase db;
    SharedPreferences shp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_property_view);
        lv = findViewById(R.id.lv_properties);
        db = new RealStateDatabase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Properties");
        shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);

        int user_id = shp_id.getInt("user_id", 0);

        ArrayList<Property> properties = new ArrayList<>();
        ArrayList<Property> propertyHouse = db.getAllProperties(RealStateDatabase.TB_HOUSE);
        ArrayList<Property> propertyVilla = db.getAllProperties(RealStateDatabase.TB_VILLA);
        ArrayList<Property> propertyAparmtment = db.getAllProperties(RealStateDatabase.TB_APARTMENT);
        ArrayList<Property> propertyFirma = db.getAllProperties(RealStateDatabase.TB_FIRMA);

        properties.addAll(propertyHouse);
        properties.addAll(propertyVilla);
        properties.addAll(propertyAparmtment);
        properties.addAll(propertyFirma);
        pa = new PropertyListAdminAdapter(properties, this);
        pa.notifyDataSetChanged();
        lv.setAdapter(pa);
    }
}