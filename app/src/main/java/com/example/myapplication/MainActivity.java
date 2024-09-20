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

        if (dbHelper.getAllRecipeDescriptions().isEmpty()) {
            long chickenSaladId = dbHelper.insertRecipeDescription(
                    "Grilled Chicken Salad",
                    "Low Fat",
                    "None",
                    30.0,
                    10.0,
                    15.0,
                    "Vitamin A, Vitamin C",
                    "A light and healthy grilled chicken salad with mixed greens and a lemon vinaigrette."
            );

            if (chickenSaladId != -1) {
                dbHelper.insertRecipeInstruction(
                        chickenSaladId,
                        "Grilled Chicken Salad",
                        "Chicken Breast",
                        "Mixed Greens",
                        "Lemon Juice",
                        "Grill the chicken breast seasoned with salt and pepper. Slice the grilled chicken and place it on a bed of mixed greens. Drizzle with lemon juice and olive oil, then sprinkle with salt and pepper to taste. Serve immediately."
                );
            }

            long steamedFishId = dbHelper.insertRecipeDescription(
                    "Steamed Fish with Vegetables",
                    "Low Fat",
                    "Fish",
                    25.0,
                    5.0,
                    10.0,
                    "Omega-3, Vitamin D",
                    "A delicate and flavorful steamed fish served with fresh vegetables."
            );

            if (steamedFishId != -1) {
                dbHelper.insertRecipeInstruction(
                        steamedFishId,
                        "Steamed Fish with Vegetables",
                        "White Fish Fillet",
                        "Carrots",
                        "Broccoli",
                        "Place the fish fillet on a steaming tray. Add sliced carrots and broccoli florets around the fish. Steam for 8-10 minutes until the fish is cooked through and vegetables are tender. Serve with a squeeze of lemon juice."
                );
            }

            long falafelId = dbHelper.insertRecipeDescription(
                    "Oven-Baked Falafel",
                    "Low Fat",
                    "None",
                    12.0,
                    6.0,
                    30.0,
                    "Fiber, Iron",
                    "A healthier version of falafel, baked in the oven until golden and crispy."
            );

            if (falafelId != -1) {
                dbHelper.insertRecipeInstruction(
                        falafelId,
                        "Oven-Baked Falafel",
                        "Chickpeas",
                        "Garlic",
                        "Parsley",
                        "Preheat the oven to 375°F. Blend chickpeas, garlic, parsley, cumin, and salt in a food processor until a coarse mixture forms. Shape into small balls and place on a baking sheet. Bake for 20-25 minutes until golden brown, flipping halfway through."
                );
            }

            long buddhaBowlId = dbHelper.insertRecipeDescription(
                    "Vegan Buddha Bowl",
                    "Vegan",
                    "None",
                    12.0,
                    20.0,
                    45.0,
                    "Vitamin A, Vitamin C, Fiber",
                    "A balanced vegan bowl filled with grains, vegetables, and a flavorful dressing."
            );

            if (buddhaBowlId != -1) {
                dbHelper.insertRecipeInstruction(
                        buddhaBowlId,
                        "Vegan Buddha Bowl",
                        "Quinoa",
                        "Chickpeas",
                        "Avocado",
                        "Cook the quinoa according to package instructions. Roast the chickpeas with olive oil and spices. Assemble the bowl with quinoa, chickpeas, avocado, kale, and other vegetables. Drizzle with tahini dressing and serve."
                );
            }

            long tofuStirFryId = dbHelper.insertRecipeDescription(
                    "Vegan Tofu Stir-Fry",
                    "Vegan",
                    "Soy",
                    18.0,
                    15.0,
                    30.0,
                    "Calcium, Iron",
                    "A quick and healthy tofu stir-fry with mixed vegetables."
            );

            if (tofuStirFryId != -1) {
                dbHelper.insertRecipeInstruction(
                        tofuStirFryId,
                        "Vegan Tofu Stir-Fry",
                        "Tofu",
                        "Broccoli",
                        "Bell Peppers",
                        "Press and cube the tofu. Stir-fry tofu in a pan until golden. Add chopped vegetables and stir-fry for 5-7 minutes. Add soy sauce and other seasonings to taste. Serve with rice or noodles."
                );
            }

            long stuffedPeppersId = dbHelper.insertRecipeDescription(
                    "Vegan Stuffed Bell Peppers",
                    "Vegan",
                    "None",
                    10.0,
                    8.0,
                    40.0,
                    "Vitamin C, Fiber",
                    "Colorful bell peppers stuffed with a flavorful quinoa and vegetable mix."
            );

            if (stuffedPeppersId != -1) {
                dbHelper.insertRecipeInstruction(
                        stuffedPeppersId,
                        "Vegan Stuffed Bell Peppers",
                        "Bell Peppers",
                        "Quinoa",
                        "Black Beans",
                        "Cook quinoa according to package instructions. Cut the tops off the bell peppers and remove seeds. Mix cooked quinoa with black beans, corn, and spices. Stuff the peppers with the mixture and bake at 375°F for 25-30 minutes."
                );
            }

            long lasagnaId = dbHelper.insertRecipeDescription(
                    "Vegetarian Lasagna",
                    "Vegetarian",
                    "Gluten, Dairy",
                    18.0,
                    25.0,
                    60.0,
                    "Vitamin A, Calcium, Iron",
                    "A rich and hearty vegetarian lasagna packed with vegetables, cheese, and marinara sauce."
            );

            if (lasagnaId != -1) {
                dbHelper.insertRecipeInstruction(
                        lasagnaId,
                        "Vegetarian Lasagna",
                        "Lasagna Noodles",
                        "Ricotta Cheese",
                        "Marinara Sauce",
                        "Cook the lasagna noodles according to package instructions. In a baking dish, layer the noodles with ricotta cheese, sautéed vegetables, and marinara sauce. Repeat layers and top with mozzarella cheese. Bake at 375°F for 40 minutes until the top is golden and bubbly."
                );
            }

            long risottoId = dbHelper.insertRecipeDescription(
                    "Mushroom Risotto",
                    "Vegetarian",
                    "None",
                    10.0,
                    15.0,
                    50.0,
                    "Vitamin D, Fiber",
                    "A creamy and savory risotto made with mushrooms and Arborio rice."
            );

            if (risottoId != -1) {
                dbHelper.insertRecipeInstruction(
                        risottoId,
                        "Mushroom Risotto",
                        "Arborio Rice",
                        "Mushrooms",
                        "Vegetable Broth",
                        "In a large pan, sauté the mushrooms until golden brown. Add Arborio rice and cook for 2 minutes. Gradually add vegetable broth, one ladle at a time, stirring constantly until the rice is creamy and cooked. Stir in Parmesan cheese and serve hot."
                );
            }

            long eggplantId = dbHelper.insertRecipeDescription(
                    "Eggplant Parmesan",
                    "Vegetarian",
                    "Gluten, Dairy",
                    12.0,
                    20.0,
                    35.0,
                    "Vitamin C, Fiber",
                    "Classic eggplant slices breaded and baked with marinara sauce and melted cheese."
            );

            if (eggplantId != -1) {
                dbHelper.insertRecipeInstruction(
                        eggplantId,
                        "Eggplant Parmesan",
                        "Eggplant",
                        "Breadcrumbs",
                        "Mozzarella Cheese",
                        "Slice the eggplant and coat in breadcrumbs. Arrange on a baking sheet and bake at 400°F for 20 minutes. Layer the baked eggplant in a dish with marinara sauce and mozzarella cheese. Bake for an additional 15 minutes until the cheese is melted and bubbly."
                );
            }

            long quinoaSaladId = dbHelper.insertRecipeDescription(
                    "Chicken & Quinoa Salad",
                    "Protein Rich",
                    "None",
                    30.0,
                    15.0,
                    40.0,
                    "Vitamin B, Fiber, Iron",
                    "A refreshing salad combining grilled chicken, quinoa, and fresh veggies."
            );

            if (quinoaSaladId != -1) {
                dbHelper.insertRecipeInstruction(
                        quinoaSaladId,
                        "Chicken & Quinoa Salad",
                        "Quinoa",
                        "Chicken Breast",
                        "Bell Peppers",
                        "Cook the quinoa as per package instructions. Grill the chicken breast until cooked through and slice it thinly. In a large bowl, combine the quinoa, chicken, chopped bell peppers, cucumbers, and a light lemon vinaigrette. Toss well and serve chilled."
                );
            }

            long chickpeaCurryId = dbHelper.insertRecipeDescription(
                    "Chickpea & Spinach Curry",
                    "Vegetarian",
                    "None",
                    12.0,
                    10.0,
                    45.0,
                    "Vitamin C, Iron, Fiber",
                    "A hearty and flavorful curry made with chickpeas and fresh spinach."
            );

            if (chickpeaCurryId != -1) {
                dbHelper.insertRecipeInstruction(
                        chickpeaCurryId,
                        "Chickpea & Spinach Curry",
                        "Chickpeas",
                        "Spinach",
                        "Coconut Milk",
                        "In a large pot, sauté onions and garlic until soft. Add chickpeas, spices, and coconut milk. Simmer for 10 minutes, then add fresh spinach. Cook for an additional 5 minutes until the spinach is wilted. Serve hot with rice or naan."
                );
            }

            long salmonId = dbHelper.insertRecipeDescription(
                    "Grilled Salmon with Veggies",
                    "Protein Rich",
                    "Fish",
                    35.0,
                    20.0,
                    5.0,
                    "Omega-3, Vitamin D",
                    "A simple and healthy grilled salmon dish served with a side of mixed vegetables."
            );

            if (salmonId != -1) {
                dbHelper.insertRecipeInstruction(
                        salmonId,
                        "Grilled Salmon with Veggies",
                        "Salmon Fillets",
                        "Asparagus",
                        "Zucchini",
                        "Season the salmon fillets with salt, pepper, and lemon juice. Grill the salmon for 5-6 minutes on each side until cooked. In the meantime, toss asparagus and zucchini with olive oil and grill for 3-4 minutes. Serve the salmon with the grilled veggies on the side."
                );
            }

            long turkeyChiliId = dbHelper.insertRecipeDescription(
                    "Turkey Chili",
                    "Protein Rich",
                    "None",
                    28.0,
                    15.0,
                    30.0,
                    "Vitamin A, Iron, Fiber",
                    "A hearty and comforting chili made with lean ground turkey and beans."
            );

            if (turkeyChiliId != -1) {
                dbHelper.insertRecipeInstruction(
                        turkeyChiliId,
                        "Turkey Chili",
                        "Ground Turkey",
                        "Kidney Beans",
                        "Tomatoes",
                        "In a large pot, brown the ground turkey with chopped onions. Add kidney beans, diced tomatoes, and chili powder. Simmer for 30 minutes, stirring occasionally. Serve hot with your favorite toppings like cheese, avocado, or sour cream."
                );
            }
        }

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
        Log.d("populateSpinners", "Category list: " + categories);
        Log.d("populateSpinners", "Allergy list: " + allergies);


        // Convert sets to lists and sort them
        List<String> categoryList = new ArrayList<>(categories);
        List<String> allergyList = new ArrayList<>(allergies);
        Collections.sort(categoryList);
        Collections.sort(allergyList);



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
            allergyList.add(1, "None");
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
                   textViewMessage.setVisibility(View.GONE);
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