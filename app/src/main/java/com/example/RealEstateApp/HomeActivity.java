package com.example.RealEstateApp;

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

import com.example.RealEstateApp.models.Property;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    RecyclerView rv;
    public static final String property_key = "property_key";
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

        Property p = new Property(R.drawable.house_2, "House", 2500, "Zahrouni", "DAR MEZYENA FI ZAHROUNI S+3", 10, "RENT");

        db.insertProduct(p, RealStateDatabase.TB_HOUSE);

        Property p1 = new Property(R.drawable.house_2, "APARTEMENT", 7600, "Zahrouni", "LOL", 5, "RENT");
        db.insertProduct(p1, RealStateDatabase.TB_APARTMENT);

        Property p2 = new Property(R.drawable.house_3, "HOUSE", 4900, "Zahrouni", "S+1", 5, "RENT");
        db.insertProduct(p2, RealStateDatabase.TB_HOUSE);

        Property p3 = new Property(R.drawable.house_4, "FIRMA", 5850, "TUNIS", "S+4", 5, "RENT");
        db.insertProduct(p3, RealStateDatabase.TB_FIRMA);

        Property p4 = new Property(R.drawable.house_5, "char gaming", 6000, "BEN AROUS", "s+2", 0, "RENT");
        db.insertProduct(p4, RealStateDatabase.TB_HOUSE);

        Property p5 = new Property(R.drawable.house_5, "FIRMA", 800, "SIDI HASSIN", "studio", 0, "SALE");
        db.insertProduct(p5, RealStateDatabase.TB_FIRMA);
        Property p6 = new Property(R.drawable.house_4, "HOUSE", 270, "SIDI HASSIN", "s+1 pas de finision", 0, "SALE");
        db.insertProduct(p6, RealStateDatabase.TB_HOUSE);
        Property p7 = new Property(R.drawable.house_3, "DAR KHLE3A", 210, "SIDI HASSIN", "VILLA 350m", 10, "SALE");
        db.insertProduct(p7, RealStateDatabase.TB_VILLA);
        Property p8 = new Property(R.drawable.house_2, "APARTEMENT", 690, "Zahrouni", "apartment s+1", 0, "SALE");
        db.insertProduct(p8, RealStateDatabase.TB_APARTMENT);
        Property p9 = new Property(R.drawable.house_2, "APARTEMENT", 45, "TUNIS", "apartment s+2", 0, "SALE");
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
                i.putExtra(property_key, productId);
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
                        Intent intent4 = new Intent(getBaseContext(), AppointmentActivity.class);
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
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_COD) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //تم الحصول عليه
            } else {

            }
        }
    }

}