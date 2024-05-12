package com.example.RealEstateApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RealEstateApp.models.Property;

import java.util.ArrayList;

public class PropertyCardActivity extends AppCompatActivity {

    RecyclerView rv;
    private PropertyAdapter adapter;
    public static final String PRODUCT_ID_KEY = "product_key";
    public static final String TABLE_NAME_KEY = "table_name_key";
    TextView tv_product_name;
    RealStateDatabase db;
    private String table_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_card);

        rv = findViewById(R.id.rv_products);
        tv_product_name = findViewById(R.id.tv_product_name);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoom_element);
        tv_product_name.setAnimation(animation);

        db = new RealStateDatabase(this);

        Intent intent = getIntent();
        String name;
        switch (MainActivity.name_data) {
            case "House":
                name = intent.getStringExtra(MainActivity.FASHION_KEY);
                tv_product_name.setText(name);
                table_name = RealStateDatabase.TB_APARTMENT;
                break;
            case "Villa":
                name = intent.getStringExtra(MainActivity.BOOK_KEY);
                tv_product_name.setText(name);
                table_name = RealStateDatabase.TB_APARTMENT;
                break;
            case "Apartment":
                name = intent.getStringExtra(MainActivity.BEAUTY_KEY);
                tv_product_name.setText(name);
                table_name = RealStateDatabase.TB_APARTMENT;
                break;
            case "Firma":
                name = intent.getStringExtra(MainActivity.ELECTRICS_KEY);
                tv_product_name.setText(name);
                table_name = RealStateDatabase.TB_APARTMENT;
                break;
        }
        MainActivity.name_data = "";

        ArrayList<Property> products = new ArrayList<>();
        products = db.getAllProperties(table_name);
        adapter = new PropertyAdapter(products, new OnRecyclerViewClickListener() {
            @Override
            public void OnItemClick(int productId) {
                Intent i = new Intent(getBaseContext(), DisplayPropertyActivity.class);
                i.putExtra(PRODUCT_ID_KEY, productId);
                i.putExtra(TABLE_NAME_KEY, table_name);
                HomeActivity.flag = false;
                startActivity(i);
            }
        });
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

    }

    @SuppressLint("ResourceAsColor")
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Property> product = db.getProductForSearch(query, table_name);
                adapter.setProducts(product);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Property> product = db.getProductForSearch(newText, table_name);
                adapter.setProducts(product);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ArrayList<Property> product = db.getAllProperties(table_name);
                adapter.setProducts(product);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

}