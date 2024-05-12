package com.example.shoppingapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DisplayPropertyActivity extends AppCompatActivity {

    RatingBar rb;
    ImageView property_img;
    TextView tv_rating, property_name, Property_price, Property_discount, Property_location, Property_description, Property_type;
    Spinner property_quantity;
    Button add_to_cart;
    double priceAfter;

    RealStateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_property);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("RealEstate App");
        rb = findViewById(R.id.ratingBar);
        property_img = findViewById(R.id.display_iv_product);
        property_name = findViewById(R.id.display_tv_name);
        Property_price = findViewById(R.id.display_tv_price);
        Property_discount = findViewById(R.id.display_tv_discount);
        Property_location = findViewById(R.id.display_tv_location);
        Property_type = findViewById(R.id.display_tv_type);
        Property_description = findViewById(R.id.display_tv_description);
        property_quantity = findViewById(R.id.display_get_quantity);
        add_to_cart = findViewById(R.id.display_btn_cart);


        int product_id;
        String table_name;
        if (HomeActivity.flag == true) {
            Intent intent = getIntent();
            product_id = intent.getIntExtra(HomeActivity.property_key, -1);
            table_name = intent.getStringExtra(HomeActivity.TABLE_NAME_KEY);
        } else {
            Intent intent = getIntent();
            product_id = intent.getIntExtra(PropertyCardActivity.PRODUCT_ID_KEY, -1);
            table_name = intent.getStringExtra(PropertyCardActivity.TABLE_NAME_KEY);
        }

        db = new RealStateDatabase(this);
        Property p = db.getProduct(product_id, table_name);

        if (p.getImage() != 0)
            property_img.setImageResource(p.getImage());
        property_name.setText(p.getName());
        if (p.getDiscount() > 0) {
            priceAfter = p.getPrice() - (p.getPrice() * (p.getDiscount() / 100));
            Property_discount.setText(priceAfter + "DT");
            Property_price.setText(p.getPrice() + "DT");
            Property_price.setPaintFlags(Property_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//وضع خط علي السعر القديم
            Property_price.setTextColor(Color.parseColor("#BFBFBF"));
        } else {
            priceAfter = p.getPrice();
            Property_discount.setText("");
            Property_price.setText(priceAfter + "DT");
            Property_price.setTextColor(Color.parseColor("#000000"));
        }
        Property_location.setText(p.getLocation());
        Property_description.setText(p.getDescription());

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int image = p.getImage();
                String name = property_name.getText().toString();
                String location = Property_location.getText().toString();
/*
                int quantity = Integer.parseInt(product_quantity.getSelectedItem().toString());
*/

                Property ppp = new Property(image, name, priceAfter, location);

                AlertDialog alertDialog = new AlertDialog.Builder(DisplayPropertyActivity.this).create();
                alertDialog.setTitle(name);
                alertDialog.setMessage("Click (Ok) TO Add This Product To Cart");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        db.insertProductInPurchases(ppp);
                        Toast.makeText(DisplayPropertyActivity.this, "Purchased Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alertDialog.show();
            }
        });

    }

    public void showDateTimePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(DisplayPropertyActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        EditText editTextDateTime = findViewById(R.id.display_get_datetime);
                                        // Format the selected date and time and set it to the EditText
                                        String selectedDateTime = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year + " " + hourOfDay + ":" + minute;
                                        editTextDateTime.setText(selectedDateTime);
                                    }
                                }, hour, minute, true);
                        timePickerDialog.show();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
