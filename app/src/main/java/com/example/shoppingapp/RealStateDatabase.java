package com.example.shoppingapp;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RealStateDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "shopping_db";
    public static final int DB_VERSION = 1;

    public static final String TB_HOUSE = "house";
    public static final String TB_APARTMENT = "apartment";
    public static final String TB_VILLA = "villa";
    public static final String TB_FIRMA = "firma";
    public static final String TB_USERS = "users";
    public static final String TB_PURCHASES = "purchases";
    public static final String TB_PROPERTY_DISCOUNT = "product_discount";

    public static final String TB_CLM_ID = "id";
    public static final String TB_CLM_IMAGE = "image";
    public static final String TB_CLM_NAME = "name";
    public static final String TB_CLM_PRICE = "price";
    public static final String TB_CLM_LOCATION = "location";
    public static final String TB_CLM_PIECES = "pieces";
    public static final String TB_CLM_DESCRIPTION = "description";
    public static final String TB_CLM_DISCOUNT = "discount";
    public static final String TB_CLM_TYPE = "type";

    public static final String TB_CLM_USER_ID = "user_id";
    public static final String TB_CLM_USER_NAME = "user_name";
    public static final String TB_CLM_USER_FULL_NAME = "full_name";
    public static final String TB_CLM_USER_PASSWORD = "user_password";
    public static final String TB_CLM_USER_EMAIL = "user_email";
    public static final String TB_CLM_USER_PHONE = "user_phone";
    public static final String TB_CLM_USER_IMAGE = "user_image";

    public RealStateDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTables(TB_HOUSE));
        sqLiteDatabase.execSQL(createTables(TB_APARTMENT));
        sqLiteDatabase.execSQL(createTables(TB_VILLA));
        sqLiteDatabase.execSQL(createTables(TB_FIRMA));
        sqLiteDatabase.execSQL(createTables(TB_PROPERTY_DISCOUNT));

        sqLiteDatabase.execSQL("CREATE TABLE " + TB_USERS + " (" + TB_CLM_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TB_CLM_USER_NAME + " TEXT UNIQUE , " +
                TB_CLM_USER_FULL_NAME + " TEXT , " + TB_CLM_USER_PASSWORD + " TEXT , " + TB_CLM_USER_EMAIL + " TEXT UNIQUE , " + TB_CLM_USER_PHONE + " TEXT , " + TB_CLM_USER_IMAGE + " TEXT );");


        sqLiteDatabase.execSQL("CREATE TABLE " + TB_PURCHASES + " (" + TB_CLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TB_CLM_IMAGE + " INTEGER , " +
                TB_CLM_NAME + " TEXT , " + TB_CLM_PRICE + " REAL , " + TB_CLM_LOCATION + " TEXT , " + TB_CLM_TYPE + " TEXT );");

        Log.d("RealStateDatabase", "Tables created successfully.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String createTables(String tableName) {
        String idColumn = "id INTEGER PRIMARY KEY AUTOINCREMENT";
        String imageColumn = "image INTEGER";
        String nameColumn = "name TEXT";
        String location = "location TEXT";
        String priceColumn = "price REAL";
        String descriptionColumn = "description TEXT";
        String discountColumn = "discount REAL";

        return "CREATE TABLE " + tableName + " (" +
                idColumn + ", " +
                imageColumn + ", " +
                nameColumn + ", " +
                location + ", " +
                priceColumn + ", " +
                descriptionColumn + ", " +
                discountColumn + ")";
    }


    public boolean insertProduct(Property p, String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_CLM_IMAGE, p.getImage());
        values.put(TB_CLM_NAME, p.getName());
        values.put(TB_CLM_PRICE, p.getPrice());
        values.put(TB_CLM_LOCATION, p.getLocation());
        values.put(TB_CLM_DESCRIPTION, p.getDescription());
        values.put(TB_CLM_DISCOUNT, p.getDiscount());

        long res = db.insert(tableName, null, values);
        db.close();
        if (p.getDiscount() > 0) {
            insertPropertyDiscount(p);
        }
        return res != -1;
    }

    public boolean insertPropertyDiscount(Property p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_CLM_IMAGE, p.getImage());
        values.put(TB_CLM_NAME, p.getName());
        values.put(TB_CLM_PRICE, p.getPrice());
        values.put(TB_CLM_LOCATION, p.getLocation());
        values.put(TB_CLM_DESCRIPTION, p.getDescription());
        values.put(TB_CLM_DISCOUNT, p.getDiscount());

        long ress = db.insert(TB_PROPERTY_DISCOUNT, null, values);
        db.close();

        return ress != -1;
    }

    public ArrayList<Property> getAllProperties(String tableName) {
        ArrayList<Property> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + "", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(TB_CLM_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(TB_CLM_DESCRIPTION));
                @SuppressLint("Range") double discount = cursor.getDouble(cursor.getColumnIndex(TB_CLM_DISCOUNT));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));

                Property p = new Property(image, name, price, location, description, discount, type);
                products.add(p);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return products;
    }

    public Property getProduct(int product_id, String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + TB_CLM_ID + " =?", new String[]{String.valueOf(product_id)});

        if (cursor.moveToFirst() && cursor != null) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(TB_CLM_ID));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(TB_CLM_IMAGE));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
            @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
            @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(TB_CLM_DESCRIPTION));
            @SuppressLint("Range") double discount = cursor.getDouble(cursor.getColumnIndex(TB_CLM_DISCOUNT));

            Property p = new Property(id, image, name, price, location, description, discount);
            cursor.close();
            db.close();
            return p;
        }
        return null;
    }

    public ArrayList<Property> getProductForSearch(String nameProduct, String tableName) {
        ArrayList<Property> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + TB_CLM_NAME + " =?", new String[]{String.valueOf(nameProduct)});

        if (cursor.moveToFirst() && cursor != null) {
            do {
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(TB_CLM_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(TB_CLM_DESCRIPTION));
                @SuppressLint("Range") double discount = cursor.getDouble(cursor.getColumnIndex(TB_CLM_DISCOUNT));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));

                Property p = new Property(image, name, price, location, description, discount, type);
                products.add(p);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return products;
    }

    public boolean deleteProduct(Property products, String tableName) {
        SQLiteDatabase database = getWritableDatabase();
        String args[] = new String[]{products.getId() + ""};
        long result = database.delete(tableName, TB_CLM_ID + "=?", args);
        return result > 0;
    }

    public boolean insertProductInPurchases(Property p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_CLM_IMAGE, p.getImage());
        values.put(TB_CLM_NAME, p.getName());
        values.put(TB_CLM_PRICE, p.getPrice());
        values.put(TB_CLM_LOCATION, p.getLocation());

        long res = db.insert(TB_PURCHASES, null, values);
        db.close();
        return res != -1;
    }

    public ArrayList<Property> getAllProductsInPurchases() {
        ArrayList<Property> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_PURCHASES + "", null);

        if (cursor.moveToFirst() && cursor != null) {
            do {
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(TB_CLM_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));

                Property p = new Property(image, name, price, location, type);
                products.add(p);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return products;
    }

    public boolean insertUser(Users user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_CLM_USER_NAME, user.getUserName());
        values.put(TB_CLM_USER_FULL_NAME, user.getFullName());
        values.put(TB_CLM_USER_PASSWORD, user.getUserPassword());
        values.put(TB_CLM_USER_EMAIL, user.getEmail());
        values.put(TB_CLM_USER_PHONE, user.getPhone());
        values.put(TB_CLM_USER_IMAGE, user.getUserImage());

        long res = db.insert(TB_USERS, null, values);
        db.close();
        return res != -1;
    }

    public Users getUser(int user_id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_USERS + " WHERE " + TB_CLM_USER_ID + " =?", new String[]{String.valueOf(user_id)});

        if (cursor.moveToFirst() && cursor != null) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(TB_CLM_USER_ID));
            @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_NAME));
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_FULL_NAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_PASSWORD));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_EMAIL));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_PHONE));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_IMAGE));

            Users user = new Users(id, userName, fullName, image, password, email, phone);
            cursor.close();
            db.close();
            return user;
        }
        return null;
    }

    public boolean upDataUser(Users user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TB_CLM_USER_FULL_NAME, user.getFullName());
        values.put(TB_CLM_USER_EMAIL, user.getEmail());
        values.put(TB_CLM_USER_PHONE, user.getPhone());
        values.put(TB_CLM_USER_IMAGE, user.getUserImage());

        String args[] = new String[]{user.getId() + ""};
        long result = db.update(TB_USERS, values, TB_CLM_USER_ID + "=?", args);
        db.close();
        return result > 0;

    }

    @SuppressLint("Range")
    public int checkUser(String user_name, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {user_name, password};
        String[] columns = {TB_CLM_USER_ID};

        Cursor cursor = db.query(TB_USERS, columns, TB_CLM_USER_NAME + " =? AND " + TB_CLM_USER_PASSWORD + " =?", selectionArgs, null, null, null);
        int id = 0;
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(TB_CLM_USER_ID));
            cursor.close();
            db.close();
            return id;
        }

        return id;
    }


    public ArrayList<Property> fetchProducts(String endpointUrl) throws MalformedURLException {
        ArrayList<Property> products = new ArrayList<>();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(endpointUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                Gson gson = new Gson();
                ArrayList<Property> productList = gson.fromJson(response.toString(), new TypeToken<ArrayList<Property>>() {
                }.getType());

                for (Property property : productList) {
                    // Convert ProductJson to Property
                    Property p = new Property(property.getName(), property.getPrice(), property.getLocation(), property.getDescription(), property.getDiscount());
                    products.add(p);
                }
            }
        } catch (IOException e) {
            Log.e("", "Error fetching products: " + e.getMessage());
            ;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("", "Error closing reader: " + e.getMessage());
                }
            }
        }

        return products;
    }

}
