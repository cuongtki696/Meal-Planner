package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Banner mBannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this); // Initialize the database

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
        mBannerView = findViewById(R.id.banner);


        if (dbHelper.getAllRecipeDescriptions().isEmpty()) {
            String steps1 = "<font color='red'>The chicken needs to be marinated for about 30 minutes.</font><br/>" +
                    "1.Marinate chicken breast with olive oil, salt, pepper, and lemon juice for 30 minutes.<br/>" +
                    "2.Grill the chicken on a preheated grill for about 6-7 minutes on each side until cooked through.<br/>" +
                    "3.Chop lettuce, cucumber, and tomato, and place them in a large bowl.<br/>" +
                    "4.Slice the grilled chicken and place it on top of the salad; toss to combine.";
            long chickenSaladId = dbHelper.insertRecipeDescription(
                    "Grilled Chicken Salad",
                    "image01",
                    "Low Fat",
                    "None",
                    30.0,
                    10.0,
                    15.0,
                    "Vitamin A, Vitamin C",
                    "A light and healthy grilled chicken salad with mixed greens and a lemon vinaigrette.",
                    "Elevate your Grilled Chicken Salad by using lean chicken breast, grilling it to retain nutrients and flavor. Bulk up on greens like kale and spinach for fiber and vitamins. Lightly dress with olive oil and lemon juice for a refreshing, low-calorie option. This meal is perfect for post-workout recovery, providing essential protein and nutrients to support your fitness journey."
                    , steps1);

            if (chickenSaladId != -1) {
                dbHelper.insertRecipeInstruction(
                        chickenSaladId,
                        "Grilled Chicken Salad",
                        "Chicken Breast",
                        "Mixed Greens",
                        "Lemon Juice",
                        "Grill the chicken breast seasoned with salt and pepper. Slice the grilled chicken and place it on a bed of mixed greens. Drizzle with lemon juice and olive oil, then sprinkle with salt and pepper to taste. Serve immediately.",
                        "https://www.youtube.com/watch?v=v1VUg6r4yFA"
                );
            }

            String steps2 = "1.Clean the fish and place ginger and garlic on top.<br/>" +
                    "2.Slice the carrot and place it with the green peas in a steamer.<br/>" +
                    "3.Steam for 15 minutes, then drizzle with soy sauce before serving.";
            long steamedFishId = dbHelper.insertRecipeDescription(
                    "Steamed Fish with Vegetables",
                    "image02",
                    "Low Fat",
                    "Fish",
                    25.0,
                    5.0,
                    10.0,
                    "Omega-3, Vitamin D",
                    "A delicate and flavorful steamed fish served with fresh vegetables.",
                    "Opt for Steamed Fish with Vegetables for a lean, protein-rich meal that's gentle on the stomach. Choose a fish high in omega-3s like salmon or trout. Pair with a variety of steamed veggies for added fiber, vitamins, and minerals. Season with herbs and spices for flavor instead of heavy sauces. This dish is ideal for promoting weight loss and supporting overall health."
                    , steps2);

            if (steamedFishId != -1) {
                dbHelper.insertRecipeInstruction(
                        steamedFishId,
                        "Steamed Fish with Vegetables",
                        "White Fish Fillet",
                        "Carrots",
                        "Broccoli",
                        "Place the fish fillet on a steaming tray. Add sliced carrots and broccoli florets around the fish. Steam for 8-10 minutes until the fish is cooked through and vegetables are tender. Serve with a squeeze of lemon juice."
                        , "https://www.youtube.com/watch?v=Nc50dc2q6oE"
                );
            }

            String steps3 = "<font color='red'>Chickpeas should be soaked overnight</font><br/>" +
                    "1.Soak chickpeas overnight and drain. Blend with onion, garlic, cilantro, and cumin until smooth.<br/>" +
                    "2.Add salt and flour, and mix well.<br/>" +
                    "3.Shape into small balls, place on a baking sheet, brush with olive oil, and bake at 180°C (350°F) for 30 minutes.";
            long falafelId = dbHelper.insertRecipeDescription(
                    "Oven-Baked Falafel",
                    "image03",
                    "Low Fat",
                    "None",
                    12.0,
                    6.0,
                    30.0,
                    "Fiber, Iron",
                    "A healthier version of falafel, baked in the oven until golden and crispy.",
                    "Embrace Oven-Baked Falafel for a healthier twist on a classic snack. Baked instead of fried, they're lower in fat and calories. Load up on veggies like lettuce, tomatoes, and cucumbers in your wrap or pita for extra fiber and nutrients. Choose whole wheat pita for added fiber. Falafel is a great source of plant-based protein, supporting muscle recovery and growth."
                    , steps3);

            if (falafelId != -1) {
                dbHelper.insertRecipeInstruction(
                        falafelId,
                        "Oven-Baked Falafel",
                        "Chickpeas",
                        "Garlic",
                        "Parsley",
                        "Preheat the oven to 375°F. Blend chickpeas, garlic, parsley, cumin, and salt in a food processor until a coarse mixture forms. Shape into small balls and place on a baking sheet. Bake for 20-25 minutes until golden brown, flipping halfway through."
                        , "https://www.youtube.com/watch?v=S8FbQQQqtE4"
                );
            }

            String steps4 = "<font color='red'>Brown rice and chickpeas (or canned) need to be cooked in advance.</font><br/>" +
                    "1.Cook brown rice and chickpeas (or use canned).<br/>" +
                    "2.Slice kale and carrot, and chop avocado.<br/>" +
                    "3.Combine all ingredients in a bowl and drizzle with tahini sauce.";
            long buddhaBowlId = dbHelper.insertRecipeDescription(
                    "Vegan Buddha Bowl",
                    "image04",
                    "Vegan",
                    "None",
                    12.0,
                    20.0,
                    45.0,
                    "Vitamin A, Vitamin C, Fiber",
                    "A balanced vegan bowl filled with grains, vegetables, and a flavorful dressing.",
                    "Indulge in a Vegan Buddha Bowl for a nutrient-dense, plant-based meal. Fill your bowl with a variety of veggies like roasted sweet potatoes, quinoa, kale, and avocado for a mix of complex carbs, healthy fats, and fiber. Top with a tahini or peanut sauce for flavor. This meal is perfect for fueling your workouts and promoting overall health and wellness."
                    , steps4);

            if (buddhaBowlId != -1) {
                dbHelper.insertRecipeInstruction(
                        buddhaBowlId,
                        "Vegan Buddha Bowl",
                        "Quinoa",
                        "Chickpeas",
                        "Avocado",
                        "Cook the quinoa according to package instructions. Roast the chickpeas with olive oil and spices. Assemble the bowl with quinoa, chickpeas, avocado, kale, and other vegetables. Drizzle with tahini dressing and serve."
                        , "https://www.youtube.com/watch?v=fj3EKszZD9E"
                );
            }

            String steps5 = "1.Cut tofu into cubes and pan-fry until golden on all sides.<br/>" +
                    "2.Add sliced bell peppers and carrot, stir-fry for 5 minutes.<br/>" +
                    "3.Add minced garlic and soy sauce, and stir-fry for another 2 minutes.";
            long tofuStirFryId = dbHelper.insertRecipeDescription(
                    "Vegan Tofu Stir-Fry",
                    "image05",
                    "Vegan",
                    "Soy",
                    18.0,
                    15.0,
                    30.0,
                    "Calcium, Iron",
                    "A quick and healthy tofu stir-fry with mixed vegetables.",
                    "Go for a Vegan Tofu Stir-Fry for a protein-packed, meatless meal. Choose firm tofu for its meaty texture and high protein content. Stir-fry with a variety of colorful veggies like bell peppers, broccoli, and mushrooms for added nutrients and fiber. Use a small amount of oil and flavor with soy sauce or tamari for a delicious, guilt-free dish that supports your fitness goals."
                    , steps5);

            if (tofuStirFryId != -1) {
                dbHelper.insertRecipeInstruction(
                        tofuStirFryId,
                        "Vegan Tofu Stir-Fry",
                        "Tofu",
                        "Broccoli",
                        "Bell Peppers",
                        "Press and cube the tofu. Stir-fry tofu in a pan until golden. Add chopped vegetables and stir-fry for 5-7 minutes. Add soy sauce and other seasonings to taste. Serve with rice or noodles."
                        , "https://www.youtube.com/watch?v=Ecq2svvSWXc");
            }

            String steps6 = "<fon color='red'>Rice should be cooked ahead of time.</font><br/>" +
                    "1.Cut the tops off the bell peppers and remove the seeds.<br/>" +
                    "2.Mix rice, black beans, corn, and tomato sauce, seasoning with salt and spices.<br/>" +
                    "3.Fill the bell peppers with the mixture and bake at 180°C (350°F) for 30 minutes.";
            long stuffedPeppersId = dbHelper.insertRecipeDescription(
                    "Vegan Stuffed Bell Peppers",
                    "image06",
                    "Vegan",
                    "None",
                    10.0,
                    8.0,
                    40.0,
                    "Vitamin C, Fiber",
                    "Colorful bell peppers stuffed with a flavorful quinoa and vegetable mix.",
                    "Embrace Vegan Stuffed Bell Peppers for a flavorful, nutrient-dense meal. Stuff bell peppers with a mix of quinoa, veggies like onions, mushrooms, and spinach, and a vegan cheese alternative. Bake until tender for a satisfying, low-fat dish. The fiber from the peppers and quinoa will keep you full, supporting your weight loss and fitness journey."
                    , steps6);

            if (stuffedPeppersId != -1) {
                dbHelper.insertRecipeInstruction(
                        stuffedPeppersId,
                        "Vegan Stuffed Bell Peppers",
                        "Bell Peppers",
                        "Quinoa",
                        "Black Beans",
                        "Cook quinoa according to package instructions. Cut the tops off the bell peppers and remove seeds. Mix cooked quinoa with black beans, corn, and spices. Stuff the peppers with the mixture and bake at 375°F for 25-30 minutes."
                        , "https://www.youtube.com/watch?v=eZRZk1x_AG8");
            }

            String steps7 = "<fon color='red'>Lasagna noodles need to be cooked beforehand.</font><br/>" +
                    "1.Cook lasagna noodles according to package instructions.</br/>" +
                    "2.Layer noodles, spinach, ricotta cheese, and tomato sauce in a baking dish.<br/>" +
                    "3.Top with mozzarella cheese and bake at 175°C (350°F) for 45 minutes.";
            long lasagnaId = dbHelper.insertRecipeDescription(
                    "Vegetarian Lasagna",
                    "image07",
                    "Vegetarian",
                    "Gluten, Dairy",
                    18.0,
                    25.0,
                    60.0,
                    "Vitamin A, Calcium, Iron",
                    "A rich and hearty vegetarian lasagna packed with vegetables, cheese, and marinara sauce.",
                    "Opt for Vegetarian Lasagna for a hearty, satisfying meal without meat. Use whole wheat lasagna noodles for added fiber. Layer with a variety of veggies like spinach, eggplant, and zucchini, and top with a rich tomato sauce and vegan cheese. Portion control is key; enjoy a moderate serving to balance indulgence with your fitness goals."
                    , steps7);

            if (lasagnaId != -1) {
                dbHelper.insertRecipeInstruction(
                        lasagnaId,
                        "Vegetarian Lasagna",
                        "Lasagna Noodles",
                        "Ricotta Cheese",
                        "Marinara Sauce",
                        "Cook the lasagna noodles according to package instructions. In a baking dish, layer the noodles with ricotta cheese, sautéed vegetables, and marinara sauce. Repeat layers and top with mozzarella cheese. Bake at 375°F for 40 minutes until the top is golden and bubbly."
                        , "https://www.youtube.com/watch?v=nVY6Ze5VYsk");
            }

            String steps8 = "<fon color='red'>Vegetable broth should be prepared in advance.</font><br/>" +
                    "1.Sauté onion and garlic, then add mushrooms and cook until golden.<br/>" +
                    "2.Stir in Arborio rice and cook for 2 minutes, gradually adding vegetable broth until absorbed.<br/>" +
                    "3.Stir in Parmesan cheese before serving.";
            long risottoId = dbHelper.insertRecipeDescription(
                    "Mushroom Risotto",
                    "image08",
                    "Vegetarian",
                    "None",
                    10.0,
                    15.0,
                    50.0,
                    "Vitamin D, Fiber",
                    "A creamy and savory risotto made with mushrooms and Arborio rice.",
                    "Indulge in Mushroom Risotto for a creamy, savory meal. Use brown rice instead of Arborio for added fiber and nutrients. Sautée mushrooms with garlic and herbs for flavor, and add a touch of vegetable broth for moisture. Enjoy in moderation, as risotto can be high in calories. Pair with a side salad for a balanced, fitness-friendly meal."
                    , steps8);

            if (risottoId != -1) {
                dbHelper.insertRecipeInstruction(
                        risottoId,
                        "Mushroom Risotto",
                        "Arborio Rice",
                        "Mushrooms",
                        "Vegetable Broth",
                        "In a large pan, sauté the mushrooms until golden brown. Add Arborio rice and cook for 2 minutes. Gradually add vegetable broth, one ladle at a time, stirring constantly until the rice is creamy and cooked. Stir in Parmesan cheese and serve hot."
                        , "https://www.youtube.com/watch?v=ju9H1RlYNxk");
            }

            String steps9 = "<fon color='red'>Prepare flour, eggs, and breadcrumbs beforehand.</font><br/>" +
                    "1.Slice eggplant and coat in flour, beaten eggs, and breadcrumbs.<br/>" +
                    "2.Pan-fry until golden on both sides.<br/>" +
                    "3.Layer eggplant, tomato sauce, and mozzarella cheese in a baking dish, then bake at 175°C (350°F) for 30 minutes.";
            long eggplantId = dbHelper.insertRecipeDescription(
                    "Eggplant Parmesan",
                    "image09",
                    "Vegetarian",
                    "Gluten, Dairy",
                    12.0,
                    20.0,
                    35.0,
                    "Vitamin C, Fiber",
                    "Classic eggplant slices breaded and baked with marinara sauce and melted cheese.",
                    "Try Eggplant Parmesan for a delicious, vegetarian twist on a classic dish. Bake sliced eggplant instead of frying for a healthier alternative. Layer with a marinara sauce and a sprinkle of vegan Parmesan cheese. Serve with a side of roasted veggies or a green salad to balance the dish and support your fitness journey."
                    , steps9);

            if (eggplantId != -1) {
                dbHelper.insertRecipeInstruction(
                        eggplantId,
                        "Eggplant Parmesan",
                        "Eggplant",
                        "Breadcrumbs",
                        "Mozzarella Cheese",
                        "Slice the eggplant and coat in breadcrumbs. Arrange on a baking sheet and bake at 400°F for 20 minutes. Layer the baked eggplant in a dish with marinara sauce and mozzarella cheese. Bake for an additional 15 minutes until the cheese is melted and bubbly."
                        , "https://www.youtube.com/watch?v=bZTI9VGQKNw");
            }

            String steps10 = "<fon color='red'>Chicken and quinoa should be cooked in advance.</font><br/>" +
                    "1.Cook chicken breast and quinoa.<br/>" +
                    "2.Combine all ingredients, drizzle with olive oil, and season with salt.";
            long quinoaSaladId = dbHelper.insertRecipeDescription(
                    "Chicken & Quinoa Salad",
                    "image10",
                    "Protein Rich",
                    "None",
                    30.0,
                    15.0,
                    40.0,
                    "Vitamin B, Fiber, Iron",
                    "A refreshing salad combining grilled chicken, quinoa, and fresh veggies.",
                    "Opt for Chicken & Quinoa Salad for a protein-rich, nutrient-dense meal. Use grilled chicken breast for lean protein and quinoa for complex carbs and fiber. Mix in fresh veggies like tomatoes, cucumbers, and red onion for added vitamins and minerals. Lightly dress with olive oil and lemon juice for a refreshing, low-calorie salad that supports your fitness goals."
                    , steps10);

            if (quinoaSaladId != -1) {
                dbHelper.insertRecipeInstruction(
                        quinoaSaladId,
                        "Chicken & Quinoa Salad",
                        "Quinoa",
                        "Chicken Breast",
                        "Bell Peppers",
                        "Cook the quinoa as per package instructions. Grill the chicken breast until cooked through and slice it thinly. In a large bowl, combine the quinoa, chicken, chopped bell peppers, cucumbers, and a light lemon vinaigrette. Toss well and serve chilled."
                        , "https://www.youtube.com/watch?v=FFpGzDuWmKA");
            }

            String steps11 = "<fon color='red'>Prepare spices and coconut milk ahead of time.</font><br/>" +
                    "1.Sauté onion and garlic, then add curry powder and cook until fragrant.<br/>" +
                    "2.Stir in chickpeas and coconut milk, simmer for 5 minutes.<br/>" +
                    "3.Add spinach and cook until wilted.";
            long chickpeaCurryId = dbHelper.insertRecipeDescription(
                    "Chickpea & Spinach Curry",
                    "image11",
                    "Vegetarian",
                    "None",
                    12.0,
                    10.0,
                    45.0,
                    "Vitamin C, Iron, Fiber",
                    "A hearty and flavorful curry made with chickpeas and fresh spinach.",
                    "Indulge in Chickpea & Spinach Curry for a flavorful, plant-based meal. Chickpeas are a great source of protein and fiber, supporting muscle recovery and satiety. Pair with nutrient-dense spinach and flavor with a homemade curry sauce. Serve over brown rice or quinoa for a complete, balanced meal that supports your fitness journey."
                    , steps11);

            if (chickpeaCurryId != -1) {
                dbHelper.insertRecipeInstruction(
                        chickpeaCurryId,
                        "Chickpea & Spinach Curry",
                        "Chickpeas",
                        "Spinach",
                        "Coconut Milk",
                        "In a large pot, sauté onions and garlic until soft. Add chickpeas, spices, and coconut milk. Simmer for 10 minutes, then add fresh spinach. Cook for an additional 5 minutes until the spinach is wilted. Serve hot with rice or naan."
                        , "https://www.youtube.com/watch?v=wsvfA0YOLUs");
            }

            String steps12 = "1.Marinate salmon with olive oil, salt, and pepper.<br/>" +
                    "2.Grill salmon and vegetables until cooked through.";
            long salmonId = dbHelper.insertRecipeDescription(
                    "Grilled Salmon with Veggies",
                    "image12",
                    "Protein Rich",
                    "Fish",
                    35.0,
                    20.0,
                    5.0,
                    "Omega-3, Vitamin D",
                    "A simple and healthy grilled salmon dish served with a side of mixed vegetables.",
                    "Go for Grilled Salmon with Veggies for a lean protein and veggie-packed meal. Salmon is rich in omega-3 fatty acids, supporting heart health and inflammation reduction. Grill salmon with herbs and lemon for a simple, flavorful preparation. Serve alongside grilled or roasted veggies like asparagus, zucchini, and bell peppers for a well-rounded, fitness-friendly dish."
                    , steps12);

            if (salmonId != -1) {
                dbHelper.insertRecipeInstruction(
                        salmonId,
                        "Grilled Salmon with Veggies",
                        "Salmon Fillets",
                        "Asparagus",
                        "Zucchini",
                        "Season the salmon fillets with salt, pepper, and lemon juice. Grill the salmon for 5-6 minutes on each side until cooked. In the meantime, toss asparagus and zucchini with olive oil and grill for 3-4 minutes. Serve the salmon with the grilled veggies on the side."
                        , "https://www.youtube.com/watch?v=-mMxDYa1tmo");
            }

            String steps13 = "<fon color='red'>Black beans and spices should be ready.</font><br/>" +
                    "1.Sauté onion, then add ground turkey and cook until browned.<br/>" +
                    "2.Stir in black beans, tomatoes, and chili powder, simmer for 30 minutes.";
            long turkeyChiliId = dbHelper.insertRecipeDescription(
                    "Turkey Chili",
                    "image13",
                    "Protein Rich",
                    "None",
                    28.0,
                    15.0,
                    30.0,
                    "Vitamin A, Iron, Fiber",
                    "A hearty and comforting chili made with lean ground turkey and beans.",
                    "Warm up with Turkey Chili for a hearty, protein-packed meal. Use lean ground turkey to keep calories in check. Load up on beans, tomatoes, and peppers for added fiber, vitamins, and minerals. Serve over brown rice or quinoa for a complete meal that supports muscle recovery and overall health during your fitness journey."
                    , steps13);

            if (turkeyChiliId != -1) {
                dbHelper.insertRecipeInstruction(
                        turkeyChiliId,
                        "Turkey Chili",
                        "Ground Turkey",
                        "Kidney Beans",
                        "Tomatoes",
                        "In a large pot, brown the ground turkey with chopped onions. Add kidney beans, diced tomatoes, and chili powder. Simmer for 30 minutes, stirring occasionally. Serve hot with your favorite toppings like cheese, avocado, or sour cream."
                        , "https://www.youtube.com/watch?v=1g7stN3Oy70");
            }
        }

        originalRecipeList = dbHelper.getAllRecipeDescriptions(); // Fetch full list of recipes
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

        List<JSONObject> imageList = new ArrayList<>();
        for (RecipeDescription description : recipeList) {
            String imageName = description.getImage();
            try {
                JSONObject item = new JSONObject();
                item.putOpt("id", description.getId());
                item.putOpt("imageName", description.getImage());
                imageList.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ApplicationInfo appInfo = getApplicationInfo();
        mBannerView.setAdapter(new BannerImageAdapter<JSONObject>(imageList) {
            @Override
            public void onBindView(BannerImageHolder holder, JSONObject data, int position, int size) {
                int resid = getResources().getIdentifier(data.optString("imageName"), "mipmap", appInfo.packageName);
                holder.imageView.setImageResource(resid);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                        intent.putExtra("recipeId", data.optInt("id")); // Assuming recipe has a unique ID
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });
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
            allergyList.add(0, "None");
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
                } else {
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