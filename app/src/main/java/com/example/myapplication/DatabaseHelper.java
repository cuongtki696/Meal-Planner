package com.example.myapplication;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mealPlanner.db";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    public static final String TABLE_STORAGE = "storage";
    public static final String TABLE_RECIPES_DESCRIPTION = "recipes_description";
    public static final String TABLE_RECIPES_INSTRUCTION = "recipes_instruction";
    public static final String TABLE_SHOPPING_LIST = "shopping_list";

    // Create Table Statements
    private static final String CREATE_TABLE_STORAGE =
            "CREATE TABLE " + TABLE_STORAGE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, quantity INTEGER, unit TEXT, used_by_date TEXT)";


    private static final String CREATE_TABLE_RECIPES_DESCRIPTION =
            "CREATE TABLE " + TABLE_RECIPES_DESCRIPTION + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "image TEXT," +
                    "category TEXT, " +
                    "allergy TEXT, " +
                    "protein REAL, " +               // protein per serving in grams
                    "fat REAL, " +                   // fat per serving in grams
                    "carbs REAL, " +                 // carbs per serving in grams
                    "micro_nutrients TEXT, " +       // other micronutrients in a formatted string
                    "fitness_advice TEXT," +
                    "process_steps TEXT,"+
                    "description TEXT)";

    private static final String CREATE_TABLE_RECIPES_INSTRUCTION =
            "CREATE TABLE " + TABLE_RECIPES_INSTRUCTION + " (" +
                    "id INTEGER PRIMARY KEY, " +  // Using the same ID as recipe_description
                    "name TEXT NOT NULL, " +
                    "ingredient1 TEXT, " +
                    "ingredient2 TEXT, " +
                    "ingredient3 TEXT, " +
                    "instructions TEXT, " +
                    "videoLink TEXT, " +
                    "FOREIGN KEY (id) REFERENCES " + TABLE_RECIPES_DESCRIPTION + "(id) ON DELETE CASCADE)";

    private static final String CREATE_TABLE_SHOPPING_LIST =
            "CREATE TABLE " + TABLE_SHOPPING_LIST + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item_name TEXT, quantity INTEGER, unit TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating tables...");
        db.execSQL(CREATE_TABLE_STORAGE);
        db.execSQL(CREATE_TABLE_RECIPES_DESCRIPTION);
        db.execSQL(CREATE_TABLE_RECIPES_INSTRUCTION);
        db.execSQL(CREATE_TABLE_SHOPPING_LIST);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORAGE);
            db.execSQL(CREATE_TABLE_RECIPES_DESCRIPTION);
            db.execSQL(CREATE_TABLE_RECIPES_INSTRUCTION);
        }
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

    // Add item to shopinglist
    public boolean insertShoppingListItem(String name, int quantity, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);

        long result = db.insert(TABLE_SHOPPING_LIST, null, values);
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

    // Get All Item in ShoppingList
    public List<ShoppingItem> getAllItem() {
        List<ShoppingItem> item = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SHOPPING_LIST, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                    String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                    item.add(new ShoppingItem(name, quantity, unit));
                }
            } finally {
                cursor.close();
            }
        }
        return item;
    }

    // Remove Item from Shopping List
    public boolean removeItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SHOPPING_LIST, "name = ?", new String[]{name}) > 0;
    }

    // Add recipe to recipe description table
    public long insertRecipeDescription(String name, String image, String category, String allergy,
                                        double protein, double fat, double carbs, String microNutrients,
                                        String description, String fitnessAdvice,String processSteps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("image", image);
        values.put("category", category);
        values.put("allergy", allergy);
        values.put("protein", protein);  // Store protein content
        values.put("fat", fat);          // Store fat content
        values.put("carbs", carbs);      // Store carbs content
        values.put("micro_nutrients", microNutrients);
        values.put("description", description);
        values.put("fitness_advice", fitnessAdvice);
        values.put("process_steps",processSteps);
        return db.insert(TABLE_RECIPES_DESCRIPTION, null, values);
    }


    // Add recipe to recipe instruction table
    public boolean insertRecipeInstruction(long id, String name, String ingredient1, String ingredient2, String ingredient3, String instructions, String videoLink) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);  // Use the ID from recipe_description
        values.put("name", name);
        values.put("ingredient1", ingredient1);
        values.put("ingredient2", ingredient2);
        values.put("ingredient3", ingredient3);
        values.put("instructions", instructions);
        values.put("videoLink", videoLink);

        long result = db.insert(TABLE_RECIPES_INSTRUCTION, null, values);
        return result != -1; // Returns true if insertion was successful
    }


    public List<String> getRecipeIngredients(long recipeId) {
        List<String> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPES_INSTRUCTION,
                new String[]{"ingredient1", "ingredient2", "ingredient3"},
                "id = ?",
                new String[]{String.valueOf(recipeId)},
                null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    String ingredient1 = cursor.getString(cursor.getColumnIndexOrThrow("ingredient1"));
                    String ingredient2 = cursor.getString(cursor.getColumnIndexOrThrow("ingredient2"));
                    String ingredient3 = cursor.getString(cursor.getColumnIndexOrThrow("ingredient3"));

                    // Add ingredients to the list if they are not null or empty
                    if (ingredient1 != null && !ingredient1.isEmpty()) ingredients.add(ingredient1);
                    if (ingredient2 != null && !ingredient2.isEmpty()) ingredients.add(ingredient2);
                    if (ingredient3 != null && !ingredient3.isEmpty()) ingredients.add(ingredient3);
                }
            } finally {
                cursor.close();
            }
        }

        return ingredients;
    }


    public List<RecipeDescription> getAllRecipeDescriptions() {
        List<RecipeDescription> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES_DESCRIPTION, null, null, null, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                    String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                    String allergy = cursor.getString(cursor.getColumnIndexOrThrow("allergy"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                    recipeList.add(new RecipeDescription(id, name, image, category, allergy, description));
                    Log.d("DatabaseHelper", "Fetched recipe: " + name);
                }
            } finally {
                cursor.close();
            }
        } else {
            Log.e("DatabaseHelper", "Cursor is null. No data fetched.");
        }
        return recipeList;

    }

    public RecipeDescription getRecipeById(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        RecipeDescription recipe = null;

        // Query to fetch the recipe description details, including nutrients
        String query = "SELECT id, name,image, category, allergy, description, protein, fat, carbs, micro_nutrients, fitness_advice,process_steps FROM recipes_description WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(recipeId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            String allergy = cursor.getString(cursor.getColumnIndexOrThrow("allergy"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            double protein = cursor.getDouble(cursor.getColumnIndexOrThrow("protein"));
            double fat = cursor.getDouble(cursor.getColumnIndexOrThrow("fat"));
            double carbs = cursor.getDouble(cursor.getColumnIndexOrThrow("carbs"));
            String microNutrients = cursor.getString(cursor.getColumnIndexOrThrow("micro_nutrients"));

            // Create the RecipeDescription object
            recipe = new RecipeDescription(id, name, category, allergy, description, protein, fat, carbs, microNutrients);

            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            recipe.setImage(image);

            String fitnessAdvice = cursor.getString(cursor.getColumnIndexOrThrow("fitness_advice"));
            recipe.setFitnessAdvice(fitnessAdvice);

            String processSteps = cursor.getString(cursor.getColumnIndexOrThrow("process_steps"));
            recipe.setProcessSteps(processSteps);

            // Load ingredients for this recipe
            recipe.loadIngredients(this);

            // Fetch instructions for the recipe from the `recipes_instruction` table
            String instructionQuery = "SELECT instructions,videoLink FROM recipes_instruction WHERE id = ?";
            Cursor instructionCursor = db.rawQuery(instructionQuery, new String[]{String.valueOf(recipeId)});
            if (instructionCursor.moveToFirst()) {
                String instructions = instructionCursor.getString(instructionCursor.getColumnIndexOrThrow("instructions"));
                recipe.setInstructions(instructions);  // Set the instructions in the RecipeDescription object
                String videoLink = instructionCursor.getString(instructionCursor.getColumnIndexOrThrow("videoLink"));
                recipe.setVideoLink(videoLink);
            }
            instructionCursor.close();
        }

        cursor.close();
        return recipe;
    }

    public List<RecipeDescription> getAllRecipes() {
        List<RecipeDescription> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT id, name, category, allergy, protein, fat, carbs, micro_nutrients, description FROM " + TABLE_RECIPES_DESCRIPTION;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String allergy = cursor.getString(cursor.getColumnIndexOrThrow("allergy"));
                double protein = cursor.getDouble(cursor.getColumnIndexOrThrow("protein"));
                double fat = cursor.getDouble(cursor.getColumnIndexOrThrow("fat"));
                double carbs = cursor.getDouble(cursor.getColumnIndexOrThrow("carbs"));
                String microNutrients = cursor.getString(cursor.getColumnIndexOrThrow("micro_nutrients"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                RecipeDescription recipe = new RecipeDescription(id, name, category, allergy, description, protein, fat, carbs, microNutrients);

                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipeList;
    }


}
