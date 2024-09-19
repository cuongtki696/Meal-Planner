package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView recipeNameTextView;
    private TextView ingredientListTextView;
    private TextView nutrientValueTextView;
    private TextView instructionTextView;
    private DatabaseHelper dbHelper;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize UI elements
        recipeNameTextView = findViewById(R.id.recipe_name);
        ingredientListTextView = findViewById(R.id.ingredient_list);
        nutrientValueTextView = findViewById(R.id.nutrient_value);
        instructionTextView = findViewById(R.id.instructions);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        recipeId = intent.getIntExtra("recipeId", -1);

        displayRecipeDetails();
    }

    private void displayRecipeDetails() {
        if (recipeId != -1) {
            // Fetch recipe details using getRecipeById
            RecipeDescription recipe = dbHelper.getRecipeById(recipeId);
            if (recipe != null) {
                // Display recipe name
                recipeNameTextView.setText(recipe.getName());

                // Display ingredients
                String ingredients = recipe.getIngredientsAsString();
                ingredientListTextView.setText(ingredients != null ? ingredients : "No ingredients available.");

                // Display nutrient information
                String nutrients = "Protein: " + recipe.getProtein() + "g\n" +
                        "Fat: " + recipe.getFat() + "g\n" +
                        "Carbs: " + recipe.getCarbs() + "g\n" +
                        "Micronutrients: " + (recipe.getMicroNutrients() != null ? recipe.getMicroNutrients() : "Not available");
                nutrientValueTextView.setText(nutrients);

                // Display instructions
                String instructions = recipe.getInstructions();
                instructionTextView.setText(instructions != null ? instructions : "No instructions available.");
            }
        }
    }


}
