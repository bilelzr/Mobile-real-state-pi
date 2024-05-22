package com.example.RealEstateApp;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.RealEstateApp.models.Property;
import com.example.RealEstateApp.models.Sales;
import com.example.RealEstateApp.models.Users;
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
    public static final String TB_SALES = "sales";
    public static final String TB_PROPERTY_DISCOUNT = "product_discount";

    public static final String TB_CLM_ID = "id";
    public static final String TB_CLM_IMAGE = "image";
    public static final String TB_CLM_NAME = "name";
    public static final String TB_CLM_PRICE = "price";
    public static final String TB_CLM_LOCATION = "location";
    public static final String TB_CLM_DESCRIPTION = "description";
    public static final String TB_CLM_DISCOUNT = "discount";
    public static final String TB_CLM_TYPE = "type";
    public static final String TB_CLM_DATE = "date";
    public static final String TB_CLM_USER_FK = "user_fk";

    public static final String TB_CLM_USER_ID = "user_id";
    public static final String TB_CLM_USER_NAME = "user_name";
    public static final String TB_CLM_USER_FULL_NAME = "full_name";
    public static final String TB_CLM_USER_PASSWORD = "user_password";
    public static final String TB_CLM_USER_EMAIL = "user_email";
    public static final String TB_CLM_USER_PHONE = "user_phone";
    public static final String TB_CLM_USER_IMAGE = "user_image";
    public static final String TB_CLM_USER_ROLE = "user_role";
    public static final String TB_CLM_USER_STATUS = "user_status";
    public static final String TB_CLM_SALES_CHECK_NUMBER = "check_number";
    public static final String TB_CLM_SALES_PAYMENT_METHOD = "payment_method";
    public static final String TB_CLM_SALES_COMMISSION = "commission";
    public static final String TB_CLM_SALES_STATUS = "satuts";

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
                TB_CLM_USER_FULL_NAME + " TEXT , " + TB_CLM_USER_PASSWORD + " TEXT , " + TB_CLM_USER_EMAIL + " TEXT UNIQUE , " + TB_CLM_USER_PHONE + " TEXT , " +
                TB_CLM_USER_IMAGE + " TEXT , " + TB_CLM_USER_ROLE + " TEXT , " + TB_CLM_USER_STATUS + " Boolean  );");


        sqLiteDatabase.execSQL("CREATE TABLE " + TB_SALES + " (" + TB_CLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + TB_CLM_IMAGE + " INTEGER , " +
                TB_CLM_NAME + " TEXT , " + TB_CLM_PRICE + " REAL , " + TB_CLM_LOCATION + " TEXT , " + TB_CLM_TYPE + " TEXT , "
                + TB_CLM_DATE + " TEXT , " + TB_CLM_USER_FK + " INTEGER , "
                + TB_CLM_SALES_PAYMENT_METHOD + " TEXT , " + TB_CLM_SALES_CHECK_NUMBER + " INTEGER , " + TB_CLM_SALES_COMMISSION + " REAL , " + TB_CLM_SALES_STATUS + " TEXT );");

        Log.d("RealStateDatabase", "Tables created successfully.");


        Users defaultUser = new Users();
        defaultUser.setEmail("admin@admin.com");
        defaultUser.setFullName("admin");
        defaultUser.setUserName("admin");
        defaultUser.setPhone("55545614");
        defaultUser.setRole("ADMIN");
        defaultUser.setUserPassword("admin");
        defaultUser.setStatus(true);

        String insertUserSQL = "INSERT INTO " + TB_USERS + " ("
                + TB_CLM_USER_NAME + ", "
                + TB_CLM_USER_FULL_NAME + ", "
                + TB_CLM_USER_PASSWORD + ", "
                + TB_CLM_USER_EMAIL + ", "
                + TB_CLM_USER_PHONE + ", "
                + TB_CLM_USER_IMAGE + ", "
                + TB_CLM_USER_ROLE + ", "
                + TB_CLM_USER_STATUS + ") VALUES ('"
                + defaultUser.getUserName() + "', '"
                + defaultUser.getFullName() + "', '"
                + defaultUser.getUserPassword() + "', '"
                + defaultUser.getEmail() + "', '"
                + defaultUser.getPhone() + "', '', '"
                + defaultUser.getRole() + "', "
                + (defaultUser.getStatus() ? 1 : 0) + ");";

        sqLiteDatabase.execSQL(insertUserSQL);
        Log.d("RealStateDatabase", "Default user inserted successfully.");
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
        String typeColumn = "type TEXT";
        String discountColumn = "discount REAL";

        return "CREATE TABLE " + tableName + " (" +
                idColumn + ", " +
                imageColumn + ", " +
                nameColumn + ", " +
                location + ", " +
                priceColumn + ", " +
                descriptionColumn + ", " +
                typeColumn + ", " +
                discountColumn + ")";
    }


    public boolean insertProperty(Property p, String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_CLM_IMAGE, p.getImage());
        values.put(TB_CLM_NAME, p.getName());
        values.put(TB_CLM_PRICE, p.getPrice());
        values.put(TB_CLM_LOCATION, p.getLocation());
        values.put(TB_CLM_DESCRIPTION, p.getDescription());
        values.put(TB_CLM_TYPE, p.getType());
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
        values.put(TB_CLM_TYPE, p.getType());
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
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_IMAGE));
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
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_IMAGE));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
            @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
            @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(TB_CLM_DESCRIPTION));
            @SuppressLint("Range") double discount = cursor.getDouble(cursor.getColumnIndex(TB_CLM_DISCOUNT));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));

            Property p = new Property(id, image, name, price, location, description, discount, type);
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
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_IMAGE));
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

    public boolean insertNewSales(Property p, String date, int userFk, String payementMethod, int checkNumber, float commission) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_CLM_IMAGE, p.getImage());
        values.put(TB_CLM_NAME, p.getName());
        values.put(TB_CLM_PRICE, p.getPrice());
        values.put(TB_CLM_TYPE, p.getType());
        values.put(TB_CLM_LOCATION, p.getLocation());
        values.put(TB_CLM_DATE, date);
        values.put(TB_CLM_USER_FK, userFk);
        values.put(TB_CLM_SALES_PAYMENT_METHOD, payementMethod);
        values.put(TB_CLM_SALES_CHECK_NUMBER, checkNumber);
        values.put(TB_CLM_SALES_CHECK_NUMBER, checkNumber);
        values.put(TB_CLM_SALES_COMMISSION, commission);
        values.put(TB_CLM_SALES_STATUS, "OPEN");
        long res = db.insert(TB_SALES, null, values);
        db.close();
        return res != -1;
    }

  /*  public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_APPOINTMENTS + "", null);

        if (cursor.moveToFirst() && cursor != null) {
            do {
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(TB_CLM_DATE));

                Property p = new Property(image, name, price, location, type);
                Appointment appointment = new Appointment(p, date);
                appointmentArrayList.add(appointment);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return appointmentArrayList;
    }*/


    public ArrayList<Sales> getAllSalesByUser(int userFk) {
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_SALES + " WHERE " + TB_CLM_USER_FK + " =?", new String[]{String.valueOf(userFk)});

        if (cursor.moveToFirst() && cursor != null) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(TB_CLM_ID));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(TB_CLM_DATE));
                @SuppressLint("Range") String paymentType = cursor.getString(cursor.getColumnIndex(TB_CLM_SALES_PAYMENT_METHOD));
                @SuppressLint("Range") Double commission = cursor.getDouble(cursor.getColumnIndex(TB_CLM_SALES_COMMISSION));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(TB_CLM_SALES_STATUS));
                Property p = new Property(image, name, price, location, type);
                Sales sales = new Sales(id,p, date,paymentType,commission.floatValue(),status);
                salesArrayList.add(sales);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return salesArrayList;
    }

    public ArrayList<Sales> getAllSales() {
        ArrayList<Sales> salesArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_SALES, null);
        if (cursor.moveToFirst() && cursor != null) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(TB_CLM_ID));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(TB_CLM_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(TB_CLM_NAME));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex(TB_CLM_PRICE));
                @SuppressLint("Range") String location = cursor.getString(cursor.getColumnIndex(TB_CLM_LOCATION));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(TB_CLM_TYPE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(TB_CLM_DATE));
                @SuppressLint("Range") String paymentType = cursor.getString(cursor.getColumnIndex(TB_CLM_SALES_PAYMENT_METHOD));
                @SuppressLint("Range") Double commission = cursor.getDouble(cursor.getColumnIndex(TB_CLM_SALES_COMMISSION));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(TB_CLM_SALES_STATUS));

                Property p = new Property(image, name, price, location, type);
                Sales sales = new Sales(id,p, date,paymentType,commission.floatValue(),status);
                salesArrayList.add(sales);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return salesArrayList;
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
        values.put(TB_CLM_USER_ROLE, user.getRole());
        if (user.getRole().equals("ADMIN")) {
            values.put(TB_CLM_USER_STATUS, false);
        }
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
    public Users getUser(String user_name, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {user_name, password};
        String[] columns = {TB_CLM_USER_ID, TB_CLM_USER_NAME, TB_CLM_USER_PASSWORD, TB_CLM_USER_EMAIL, TB_CLM_USER_FULL_NAME, TB_CLM_USER_PHONE, TB_CLM_USER_IMAGE, TB_CLM_USER_ROLE};

        Cursor cursor = db.query(TB_USERS, columns, TB_CLM_USER_NAME + " =? AND " + TB_CLM_USER_PASSWORD + " =?", selectionArgs, null, null, null);

        Users user = null;

        if (cursor != null && cursor.moveToFirst()) {
            user = new Users();
            user.setId(cursor.getInt(cursor.getColumnIndex(TB_CLM_USER_ID)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_NAME)));
            user.setUserPassword(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_EMAIL)));
            user.setFullName(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_FULL_NAME)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_PHONE)));
            user.setUserImage(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_IMAGE)));
            user.setRole(cursor.getString(cursor.getColumnIndex(TB_CLM_USER_ROLE)));
        }

        if (cursor != null)
            cursor.close();

        db.close();

        return user;
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

    public void updateSaleStatusToRejected(int saleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_CLM_SALES_STATUS, "rejected");

        String whereClause = TB_CLM_ID + " = ?";
        String[] whereArgs = {String.valueOf(saleId)};

        db.update(TB_SALES, values, whereClause, whereArgs);
        db.close();

    }

    public void updateSaleStatusToAccepted(int saleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_CLM_SALES_STATUS, "accepted");

        String whereClause = TB_CLM_ID + " = ?";
        String[] whereArgs = {String.valueOf(saleId)};

        db.update(TB_SALES, values, whereClause, whereArgs);
        db.close();
    }

}
