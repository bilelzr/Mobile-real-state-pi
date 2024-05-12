package com.example.RealEstateApp.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.AppointmentActivity;
import com.example.RealEstateApp.ProfileActivity;
import com.example.RealEstateApp.R;
import com.example.RealEstateApp.RealStateDatabase;
import com.example.RealEstateApp.models.Property;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AdminViewPropertyActivity extends AppCompatActivity {

    BottomNavigationView bnv;


    FloatingActionButton addProp;
    ListView lv;
    PropertyListAdminAdapter pa;
    RealStateDatabase db;
    SharedPreferences shp_id;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_property_view);
        lv = findViewById(R.id.lv_properties);
        db = new RealStateDatabase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Properties");


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

        bnv = findViewById(R.id.bottom_navigation_view_admin);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.property:
                        Intent intent1 = new Intent(getBaseContext(), AdminViewPropertyActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.profile:
                        Intent intent2 = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.appointments:
                        Intent intent3 = new Intent(getBaseContext(), AppointmentActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.Benefits:
                        Intent intent4 = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(intent4);
                        break;
                }

                return true;
            }
        });


        addProp = findViewById(R.id.property_add);
        addProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the action to start the new activity here
                Intent intent = new Intent(AdminViewPropertyActivity.this, AddPropertyActivity.class);
                startActivity(intent);
            }
        });



    }





}