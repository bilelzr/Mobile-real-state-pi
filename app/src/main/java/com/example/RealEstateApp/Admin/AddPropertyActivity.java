package com.example.RealEstateApp.Admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.R;
import com.example.RealEstateApp.RealStateDatabase;
import com.example.RealEstateApp.models.Property;

public class AddPropertyActivity extends AppCompatActivity {


    EditText et_property_name_add, et_property_description_add, et_property_location_add, et_property_type_add, et_property_price_add;

    Spinner sp_property_category;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        et_property_name_add = findViewById(R.id.et_property_name_add);
        et_property_description_add = findViewById(R.id.et_property_description_add);
        et_property_location_add = findViewById(R.id.et_property_location_add);
        et_property_type_add = findViewById(R.id.et_property_type_add);
        et_property_price_add = findViewById(R.id.et_property_price_add);

        sp_property_category = findViewById(R.id.sp_property_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.property_categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_property_category.setAdapter(adapter);


        Button pickImageButton = findViewById(R.id.btn_pick_image);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button addButton = findViewById(R.id.btn_addProperty);
        RealStateDatabase db = new RealStateDatabase(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = 0;

                if (et_property_name_add.getText().toString().isEmpty()) {
                    et_property_name_add.setError("Please Enter the name of the property");
                } else {
                    c++;
                }
                if (et_property_description_add.getText().toString().isEmpty()) {
                    et_property_description_add.setError("Please Enter a description");
                } else {
                    c++;
                }
                if (et_property_location_add.getText().toString().isEmpty()) {
                    et_property_location_add.setError("Please Enter the location");
                } else {
                    c++;
                }
                if (et_property_type_add.getText().toString().isEmpty()) {
                    et_property_price_add.setError("Please Enter Your price");
                } else {
                    c++;
                }

                if (et_property_price_add.getText().toString().isEmpty()) {
                    et_property_price_add.setError("Please Enter Your password");
                } else {
                    c++;
                }
                if (sp_property_category.getSelectedItem() != null) {
                    System.out.println("h");
                } else {
                    c++;
                }
//    public Property(String image, String name, double price, String location, String description, double discount, String type) {

                if (c == 5) {
                    Property property = new Property(
                            String.valueOf(R.drawable.house_2),
                            et_property_name_add.getText().toString(),
                            Double.parseDouble(et_property_price_add.getText().toString()),
                            et_property_location_add.getText().toString(),
                            et_property_description_add.getText().toString(),
                            0, et_property_type_add.getText().toString());
                    String tableName = "NaN";
                    if (sp_property_category.getSelectedItem().toString().equals("Villa")) {
                        tableName = "villa";
                    } else if (sp_property_category.getSelectedItem().toString().equals("Apartment")) {
                        tableName = "apartment";

                    } else if (sp_property_category.getSelectedItem().toString().equals("House")) {
                        tableName = "house";
                    } else if (sp_property_category.getSelectedItem().toString().equals("Firma")) {
                        tableName = "firma";
                    }
                    db.insertProperty(property, tableName);
                    Toast.makeText(AddPropertyActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddPropertyActivity.this, AdminViewPropertyActivity.class));
                    finish();
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            // Now you can do whatever you want with the selected image URI, like displaying it in an ImageView
            // For example:
            // ImageView imageView = findViewById(R.id.imageView);
            // imageView.setImageURI(imageUri);
        }
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
}