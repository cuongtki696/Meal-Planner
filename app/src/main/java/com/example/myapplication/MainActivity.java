package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<RecipeDescription> recipeList;
    private Spinner spinnerCategory, spinnerAllergy;
    private Button buttonApplyFilter;
    private List<RecipeDescription> originalRecipeList; // Store the full list of recipes
    private TextView textViewMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this); // Initialize the database
        originalRecipeList = dbHelper.getAllRecipeDescriptions(); // Fetch full list of recipes

        // Set up button navigation
        Button buttonHome = findViewById(R.id.button_home);
        Button buttonStorage = findViewById(R.id.button_storage);
        Button buttonShoppingList = findViewById(R.id.button_shopping_list);
        buttonHome.setOnClickListener(v -> {
        });

        buttonStorage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StorageActivity.class);
            startActivity(intent);
        });

        buttonShoppingList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
        });

        // Initialize UI elements
        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerAllergy = findViewById(R.id.spinner_allergy);
        buttonApplyFilter = findViewById(R.id.button_apply_filter);
        textViewMessage = findViewById(R.id.textView_message);

        // Populate spinners with data
        populateSpinners();

        // Set up the RecyclerView
        setupRecyclerView(originalRecipeList);

        // Set up filter button click listener
        buttonApplyFilter.setOnClickListener(v -> applyFilter());

        // Retrieve recipes from the database
        recipeList = dbHelper.getAllRecipeDescriptions();

        // Load ingredients for each recipe
        for (RecipeDescription recipe : recipeList) {
            recipe.loadIngredients(dbHelper);
        }

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        recipeAdapter.notifyDataSetChanged();
    }

    private void populateSpinners() {
        // Get unique categories and allergies from the recipe list
        Set<String> categories = new HashSet<>();
        Set<String> allergies = new HashSet<>();
        for (RecipeDescription recipe : originalRecipeList) {
            categories.add(recipe.getCategory());
            allergies.add(recipe.getAllergy());
        }

        // Convert sets to lists and sort them
        List<String> categoryList = new ArrayList<>(categories);
        List<String> allergyList = new ArrayList<>(allergies);
        Collections.sort(categoryList);
        Collections.sort(allergyList);

        // Add "All" to the top of the category list
        if (!categoryList.contains("All")) {
            categoryList.add(0, "All");
        }

        // Add "Budget" after "All"
        if (!categoryList.contains("Budget")) {
            categoryList.add(1, "Budget");
        }

        // Add "None" to the top of the allergy list
        if (!allergyList.contains("None")) {
            allergyList.add(0, "None");
        }

        // Create ArrayAdapters for spinners
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> allergyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allergyList);
        allergyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAllergy.setAdapter(allergyAdapter);

        // Set the default selection for the allergy spinner to "None"
        int nonePosition = allergyAdapter.getPosition("None");
        if (nonePosition >= 0) {
            spinnerAllergy.setSelection(nonePosition);
        }
    }

    private void applyFilter() {
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        String selectedAllergy = spinnerAllergy.getSelectedItem().toString();

        List<RecipeDescription> filteredRecipes = new ArrayList<>();

        if (selectedCategory.equals("Budget")) {
           List<Ingredient> storageIngredients = dbHelper.getAllIngredients();
            Map<String, Integer> storageMap = new HashMap<>();
            for (Ingredient ingredient : storageIngredients) {
                storageMap.put(ingredient.getName(), ingredient.getQuantity());
            }

           for (RecipeDescription recipe : originalRecipeList) {
                List<String> requiredIngredients = dbHelper.getRecipeIngredients(recipe.getId());
                boolean canMake = true;
                for (String required : requiredIngredients) {
                    if (!storageMap.containsKey(required) || storageMap.get(required) <= 0) {
                        canMake = false;
                        break;
                    }
                }

               if (canMake && (selectedAllergy.equals("None") || !recipe.getAllergy().equals(selectedAllergy))) {
                    filteredRecipes.add(recipe);
                }
               else {
                   textViewMessage.setVisibility(View.VISIBLE);
               }
            }
        } else {
            for (RecipeDescription recipe : originalRecipeList) {
                boolean matchesCategory = selectedCategory.equals("All") || recipe.getCategory().equals(selectedCategory);
                boolean matchesAllergy = selectedAllergy.equals("None") || !recipe.getAllergy().equals(selectedAllergy);

                if (matchesCategory && matchesAllergy) {
                    filteredRecipes.add(recipe);
                }
            }
            textViewMessage.setVisibility(filteredRecipes.isEmpty() ? View.VISIBLE : View.GONE);
        }

        recipeAdapter.updateRecipeList(filteredRecipes);
    }

    private List<RecipeDescription> getAvailableRecipes(List<RecipeDescription> filteredRecipes) {
        List<RecipeDescription> availableRecipes = new ArrayList<>();

        // Retrieve storage ingredients
        List<Ingredient> storageIngredients = dbHelper.getAllIngredients();
        Map<String, Integer> storageMap = new HashMap<>();
        for (Ingredient ingredient : storageIngredients) {
            storageMap.put(ingredient.getName(), ingredient.getQuantity());
        }

        // Check each filtered recipe against available ingredients
        for (RecipeDescription recipe : filteredRecipes) {
            List<String> requiredIngredients = dbHelper.getRecipeIngredients(recipe.getId());
            boolean canMake = true;

            for (String required : requiredIngredients) {
                if (!storageMap.containsKey(required) || storageMap.get(required) <= 0) {
                    canMake = false;
                    break;
                }
            }

            if (canMake) {
                availableRecipes.add(recipe);
            }
        }
        return availableRecipes;
    }



    private void setupRecyclerView(List<RecipeDescription> recipes) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, recipes);
        recyclerView.setAdapter(recipeAdapter);
    }


}