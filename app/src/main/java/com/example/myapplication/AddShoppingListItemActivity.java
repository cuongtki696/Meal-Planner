package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddShoppingListItemActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list_item);

        dbHelper = new DatabaseHelper(this);

        EditText itemNameInput = findViewById(R.id.shopping_list_item_name_input);
        EditText itemQuantityInput = findViewById(R.id.shopping_list_item_quantity_input);
        EditText itemUnitInput = findViewById(R.id.shopping_list_item_unit_input);
        Button addButton = findViewById(R.id.button_add_shopping_list_item);

        // Handle add item button
        addButton.setOnClickListener(v -> {
            String itemName = itemNameInput.getText().toString();
            String itemQuantityStr = itemQuantityInput.getText().toString();
            String itemUnit = itemUnitInput.getText().toString();

            if (itemName.isEmpty() || itemQuantityStr.isEmpty() || itemUnit.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Capitalize the first letter of each word in the item name
            String capitalizedItemName = capitalizeWords(itemName);

            int itemQuantity = Integer.parseInt(itemQuantityStr);

            // Insert the new shopping list item into the database
            boolean success = dbHelper.insertShoppingListItem(capitalizedItemName, itemQuantity, itemUnit);
            if (success) {
                Toast.makeText(this, "Item added to shopping list successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add item to shopping list", Toast.LENGTH_SHORT).show();
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
