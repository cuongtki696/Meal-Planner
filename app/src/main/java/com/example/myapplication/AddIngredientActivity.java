package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        dbHelper = new DatabaseHelper(this);

        EditText nameInput = findViewById(R.id.ingredient_name_input);
        EditText quantityInput = findViewById(R.id.ingredient_quantity_input);
        EditText unitInput = findViewById(R.id.ingredient_unit_input);
        EditText usedByDateInput = findViewById(R.id.ingredient_used_by_date_input);
        Button addButton = findViewById(R.id.button_add_ingredient);


        // Handle add ingredient button
        addButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String quantityStr = quantityInput.getText().toString();
            String unit = unitInput.getText().toString();
            String usedByDate = usedByDateInput.getText().toString();

            if (name.isEmpty() || quantityStr.isEmpty() || unit.isEmpty() || usedByDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Capitalize the first letter of each word in the ingredient name
            String capitalizedName = capitalizeWords(name);

            int quantity = Integer.parseInt(quantityStr);

            // Insert the new ingredient into the database
            boolean success = dbHelper.insertIngredient(capitalizedName, quantity, unit, usedByDate);
            if (success) {
                Toast.makeText(this, "Ingredient added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add ingredient", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split(" ");
        StringBuilder capitalizedWords = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                capitalizedWords.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalizedWords.toString().trim();
    }


}
