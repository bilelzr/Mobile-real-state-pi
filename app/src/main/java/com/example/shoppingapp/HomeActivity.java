package com.example.shoppingapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    RecyclerView rv;
    public static final String PRODUCT_KEY = "product_key";
    private static final int PERMISSION_REQ_COD = 1;
    public static final String TABLE_NAME_KEY = "table_key";
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    SharedPreferences shp_id;
    SharedPreferences.Editor shpEditor_id;
    public static boolean flag;
    RealStateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        flag = false;
        db = new RealStateDatabase(this);

        //    int image, String name, double price, String brand, int pieces, String description, double discount, float rating
        Property p = new Property(R.drawable.house_2, "playstation4", 2500, "sony", "computer noc Barnes & Noble Retail and Samsung Electronics launched the new Samsung Galaxy Tab 4 Noc tablet in New York.", 10, (float) 3.5);

        db.insertProduct(p, RealStateDatabase.TB_APARTMENT);

        Property p1 = new Property(R.drawable.house_2, "playstation5&4", 7600, "sony", "Sony PlayStation 5 Console + 2 DualSense Wireless Controller+ fifa 21 standard edition All prices include VAT..", 25, (float) 3.5);
        db.insertProduct(p1, RealStateDatabase.TB_APARTMENT);

        Property p2 = new Property(R.drawable.house_3, "pes2012", 4900, "Konami", "AUTHENTIC LEAGUES - Fully licensed leagues are coming to PES 2012. Details to be revealed soon!", 0, (float) 3.5);
        db.insertProduct(p2, RealStateDatabase.TB_APARTMENT);

        Property p3 = new Property(R.drawable.house_4, "menacing", 5850, "acer", "Acer Predator Thronos Air, Gaming Throne with Massage Pad and Gaming Chair, Up to 3 Displays, 130 Degrees Recline, LED Lighting, PC Landing Pad, Stabilizing Arm (PC and Monitors Sold Separately)", 0, (float) 3.5);
        db.insertProduct(p3, RealStateDatabase.TB_APARTMENT);

        Property p4 = new Property(R.drawable.house_5, "char gaming", 6000, "acer", "different colors gaming Thermaltake U-Fit Black-Red Gaming Chair GGC-UFT-BRMWDS-01 GGC-UFT-BRMWDS-01", 0, (float) 3.5);
        db.insertProduct(p4, RealStateDatabase.TB_APARTMENT);

        Property p5 = new Property(R.drawable.house_5, "gamecontroller5", 800, " Powerextra", "Powerextra Xbox 360 Controllers,USB Gamepad Wired Controller Improved Ergonomic Joystick Dual Vibration,Compatible with Xbox 360 Slim/Xbox 360/PC(Windows / 7/8.1/10),Black", 0, (float) 3.5);
        db.insertProduct(p5, RealStateDatabase.TB_APARTMENT);
        Property p6 = new Property(R.drawable.house_4, "Microwave", 270, "TOSHIBA", "Microwave is an electric device used to heat various types of foods, and this type of oven is different from traditional ovens", 0, (float) 3.5);
        db.insertProduct(p6, RealStateDatabase.TB_APARTMENT);
        Property p7 = new Property(R.drawable.house_3, "vacuum cleaner", 210, "TOSHIBA", "Hoover Vacuum Cleaner - 2000W, Black - Red TCP2010020 Buy with installments and pay EGP 51.06 for 48 months with select banks.", 10, (float) 3.5);
        db.insertProduct(p7, RealStateDatabase.TB_APARTMENT);
        Property p8 = new Property(R.drawable.house_2, "Washer", 690, "TOSHIBA", "TOSHIBA Washing Machine Half Automatic Capacity : 12 Kg Max Spin Speed : 1400 RPM With 2 Motors Washing Machine Giant Super Size Works With All Kinds of Regular Powders Vortexes System", 0, (float) 3.5);
        db.insertProduct(p8, RealStateDatabase.TB_APARTMENT);
        Property p9 = new Property(R.drawable.house_2, "t_shirt", 45, "Zara", "Black t_shirt size XL.", 0, (float) 3.5);
        db.insertProduct(p9, RealStateDatabase.TB_APARTMENT);
/*
        Products p = new Products(15);
        db.deleteProduct(p,ShoppingDatabase.TB_FASHION);*/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_COD);
        }


        rv = findViewById(R.id.rv_home);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);

        ArrayList<Property> products1 = new ArrayList<>();

            products1 = db.getAllProperties(RealStateDatabase.TB_PROPERTY_DISCOUNT);


        HomeAdabter adapter = new HomeAdabter(products1, new OnRecyclerViewClickListener() {
            @Override
            public void OnItemClick(int productId) {
                Intent i = new Intent(getBaseContext(), DisplayPropertyActivity.class);
                i.putExtra(PRODUCT_KEY, productId);
                i.putExtra(TABLE_NAME_KEY, RealStateDatabase.TB_PROPERTY_DISCOUNT);
                flag = true;
                startActivity(i);
            }
        });
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        bnv = findViewById(R.id.BottomNavigationView);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent1 = new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.products:
                        Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.profile:
                        Intent intent3 = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.basket:
                        Intent intent4 = new Intent(getBaseContext(), PurchasesActivity.class);
                        startActivity(intent4);
                        break;
                }

                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                break;
            case R.id.settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        try {
            if (shp == null) {
                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
            }
            shpEditor = shp.edit();
            shpEditor.putInt("user", 0);
            shpEditor.commit();

            if (shp_id == null) {
                shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);
            }
            shpEditor_id = shp_id.edit();
            shpEditor_id.putInt("user_id", 0);
            shpEditor_id.commit();

            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ_COD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //تم الحصول عليه
                } else {

                }
        }
    }

}