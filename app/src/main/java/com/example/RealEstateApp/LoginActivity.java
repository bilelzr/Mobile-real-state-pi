package com.example.RealEstateApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.RealEstateApp.Admin.AdminViewPropertyActivity;
import com.example.RealEstateApp.models.Users;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText user_name, user_password;
    CheckBox remember_me;
    TextView error;
    Button btn_login;
    TextView btn_register;
    RealStateDatabase db;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    SharedPreferences shp_id;
    SharedPreferences.Editor shpEditor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_register = findViewById(R.id.tv_register_from_login);
        btn_login = findViewById(R.id.btnlogin);
        user_name = findViewById(R.id.et_user_name_login);
        user_password = findViewById(R.id.et_password_login);
        remember_me = findViewById(R.id.chb_remember_login);
        error = findViewById(R.id.tv_login_error);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);
        checkLogin();

        db = new RealStateDatabase(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int c = 0;
                if (user_name.getText().toString().isEmpty()) {
                    user_name.setError("Enter User Name");
                } else {
                    c++;
                }
                if (user_password.getText().toString().isEmpty()) {
                    user_password.setError("Enter Your Password");
                } else {
                    c++;
                }
                Users user = db.getUser(user_name.getText().toString(), user_password.getText().toString());

                if (c == 2) {
                    int id = 0;

                    if (user != null) {
                        id = user.getId();
                    }
                    Toast.makeText(LoginActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                    try {
                        if (id > 0) {
                            if (shp == null) {
                                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
                            }
                            if (shp_id == null) {
                                shp_id = getSharedPreferences("Preferences_id", MODE_PRIVATE);
                            }
                            shpEditor_id = shp_id.edit();
                            shpEditor_id.putInt("user_id", id);
                            shpEditor_id.putString("user_role", user.getRole());
                            shpEditor_id.commit();

                            if (remember_me.isChecked()) {
                                shpEditor = shp.edit();
                                shpEditor.putInt("user", id);
                                shpEditor.putString("user_role", user.getRole());
                                shpEditor.commit();
                            }

                            if (user.getRole().equals("ADMIN")) {
                                startActivity(new Intent(LoginActivity.this, AdminViewPropertyActivity.class));
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }
                            finish();
                        } else {
                            error.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception ex) {
                        error.setText(ex.getMessage().toString());
                    }

                }

            }
        });

    }

    public void checkLogin() {
        if (shp == null) {
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        }
        int user_id = shp.getInt("user", 0);

        if (user_id != 0) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

}