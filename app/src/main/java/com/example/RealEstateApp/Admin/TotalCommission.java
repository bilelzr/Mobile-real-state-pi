package com.example.RealEstateApp.Admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.R;
import com.example.RealEstateApp.RealStateDatabase;

public class TotalCommission extends AppCompatActivity {


    RealStateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_commission);
        db = new RealStateDatabase(this);
        db.calculateSumOfAcceptedCommissions();
        // Find the EditText by its ID
        TextView editText = findViewById(R.id.tv_commision_property);

        // Set the text to 2000
        editText.setText(String.valueOf(db.calculateSumOfAcceptedCommissions()));
    }
}