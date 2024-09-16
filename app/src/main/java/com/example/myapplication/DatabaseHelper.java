package com.example.myapplication;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mealPlanner.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_STORAGE = "storage";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_SHOPPING_LIST = "shopping_list";

    // Create Table Statements
    private static final String CREATE_TABLE_STORAGE =
            "CREATE TABLE " + TABLE_STORAGE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, quantity INTEGER, unit TEXT, used_by_date TEXT)";


    private static final String CREATE_TABLE_RECIPES =
            "CREATE TABLE " + TABLE_RECIPES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, ingredients TEXT, instructions TEXT)";

    private static final String CREATE_TABLE_SHOPPING_LIST =
            "CREATE TABLE " + TABLE_SHOPPING_LIST + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item_name TEXT, quantity INTEGER, unit TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all tables
        db.execSQL(CREATE_TABLE_STORAGE);
        db.execSQL(CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_SHOPPING_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
        // Create new tables
        onCreate(db);
    }

    // Add ingredient to storage
    public boolean insertIngredient(String name, int quantity, String unit, String usedByDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("used_by_date", usedByDate);

        long result = db.insert("storage", null, values);
        return result != -1; // Return true if insertion was successful
    }

    // Get All Ingredient in storage
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STORAGE, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                    String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                    String usedByDate = cursor.getString(cursor.getColumnIndexOrThrow("used_by_date"));
                    ingredients.add(new Ingredient(name, quantity, unit, usedByDate));
                }
            } finally {
                cursor.close();
            }
        }
        return ingredients;
    }

    // Remove ingredient from Storage Table
    public boolean deleteIngredient(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STORAGE, "name = ?", new String[]{name}) > 0;
    }

}
