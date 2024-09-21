package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView recipeNameTextView;
    private TextView ingredientListTextView;
    private TextView nutrientValueTextView;
    private TextView instructionTextView;
    private TextView videoLinkTextView;
    private TextView fitnessAdviceTextView;
    private TextView processStepsTextView;
    private ImageView imageView;
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
        videoLinkTextView = findViewById(R.id.videoLink);
        fitnessAdviceTextView = findViewById(R.id.fitness_advice);
        processStepsTextView = findViewById(R.id.process_steps);
        imageView = findViewById(R.id.iv_image);

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

                int imageResId = getResources().getIdentifier(recipe.getImage(), "mipmap", getApplicationInfo().packageName);
                imageView.setImageResource(imageResId);

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

                videoLinkTextView.setText(recipe.getVideoLink());

                fitnessAdviceTextView.setText(recipe.getFitnessAdvice());
                Log.e("TAG", "fitnessAdvice=" + recipe.getFitnessAdvice());

                String processSteps = recipe.getProcessSteps();
                processStepsTextView.setText(Html.fromHtml(processSteps));
            }
        }
    }


}
