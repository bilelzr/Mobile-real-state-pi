package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView house_card, book_card, beauty_card, villa_card, game_card, homeCooker_card, firma_card, apartment_card, sports_card, carTools_card;
    TextView tv_house, tv_book, tv_beauty, tv_villa, tv_game, tv_home, tv_firma, tv_apartment, tv_sports, tv_car;
    public static final String FASHION_KEY = "fashion_key";
    public static final String BOOK_KEY = "book_key";
    public static final String BEAUTY_KEY = "beauty_key";
    public static final String ELECTRICS_KEY = "electrics_key";
    public static final String GAME_KEY = "game_key";
    public static final String HOME_KEY = "home_key";
    public static final String LAPTOP_KEY = "laptop_key";
    public static final String MOBILE_KEY = "mobile_key";
    public static final String SPORTS_KEY = "sports_key";
    public static final String CAR_KEY = "car_key";
    public static String name_data = "";

    RealStateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        house_card = findViewById(R.id.house_card);
        villa_card = findViewById(R.id.villa_card);
        apartment_card = findViewById(R.id.apartment_card);
        firma_card = findViewById(R.id.firma_card);
        tv_house = findViewById(R.id.tv_house_card);
        tv_villa = findViewById(R.id.tv_villa_card);
        tv_apartment = findViewById(R.id.tv_apartment_card);
        tv_firma = findViewById(R.id.tv_firma_card);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Properties");


        db = new RealStateDatabase(this);
//        Products p = new Products(R.drawable.python,"python",32.0,"Electronic Book",23,"Python is a computer programming language often used to build websites and software, automate tasks, and conduct data analysis.",0);
//
//        db.insertProduct(p,ShoppingDatabase.TB_BOOK);

        house_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_data = tv_house.getText().toString();
                Intent intent = new Intent(getBaseContext(), PropertyCardActivity.class);
                intent.putExtra(FASHION_KEY, tv_house.getText().toString());
                startActivity(intent);
            }
        });


        villa_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_data = tv_villa.getText().toString();
                Intent intent = new Intent(getBaseContext(), PropertyCardActivity.class);
                intent.putExtra(ELECTRICS_KEY, tv_villa.getText().toString());
                startActivity(intent);
            }
        });


        firma_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_data = tv_firma.getText().toString();
                Intent intent = new Intent(getBaseContext(), PropertyCardActivity.class);
                intent.putExtra(LAPTOP_KEY, tv_firma.getText().toString());
                startActivity(intent);
            }
        });

        apartment_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_data = tv_apartment.getText().toString();
                Intent intent = new Intent(getBaseContext(), PropertyCardActivity.class);
                intent.putExtra(MOBILE_KEY, tv_apartment.getText().toString());
                startActivity(intent);
            }
        });


    }
}