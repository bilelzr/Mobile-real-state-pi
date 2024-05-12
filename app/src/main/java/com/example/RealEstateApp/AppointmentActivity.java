package com.example.RealEstateApp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.models.Appointment;

import java.util.ArrayList;

public class AppointmentActivity extends AppCompatActivity {

    ListView lv;
    AppointmentAdapter pa;
    RealStateDatabase db;
    SharedPreferences shp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        lv = findViewById(R.id.lv_appointments);

        db = new RealStateDatabase(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Appointments");
        shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);

        int user_id = shp_id.getInt("user_id", 0);

        ArrayList<Appointment> Appointment = new ArrayList<>();
        Appointment = db.getAllAppointmentsByUser(user_id);
        pa = new AppointmentAdapter(Appointment, this);
        pa.notifyDataSetChanged();
        lv.setAdapter(pa);

    }
}