package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.util.SparseBooleanArray;
import android.util.Log;

public class StorageActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private IngredientAdapter adapter;
    private List<Ingredient> ingredientList;
    private TextView storageEmptyMessage;
    private Button removeSelectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.ingredient_recycler_view);
        storageEmptyMessage = findViewById(R.id.storage_empty_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get ingredients from the database
        ingredientList = dbHelper.getAllIngredients();
        Log.d("StorageActivity", "Number of ingredients: " + (ingredientList != null ? ingredientList.size() : "null"));

        if (ingredientList == null || ingredientList.isEmpty()) {
            storageEmptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            storageEmptyMessage.setVisibility(View.GONE);
            adapter = new IngredientAdapter(this, ingredientList);
            recyclerView.setAdapter(adapter);
        }

        // Set up the navigation bar
        Button homeButton = findViewById(R.id.button_home);
        Button storageButton = findViewById(R.id.button_storage);
        Button shoppingListButton = findViewById(R.id.button_shopping_list);
        Button removeButton = findViewById(R.id.button_remove);
        Button updateButton = findViewById(R.id.button_update);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(StorageActivity.this, MainActivity.class);
            startActivity(intent);
        });

        storageButton.setOnClickListener(v -> {
            // Current Activity is StorageActivity, do nothing
        });

        shoppingListButton.setOnClickListener(v -> {
            Intent intent = new Intent(StorageActivity.this, ShoppingListActivity.class);
            startActivity(intent);
        });

        // Set up the Remove button
        removeButton.setOnClickListener(v -> {
            if (adapter.isSelectionMode()) {
                // If in selection mode, perform the removal
                removeSelectedIngredients();
                // Change button text back to "Remove"
                removeButton.setText("Remove");
                adapter.setSelectionMode(false);
            } else {
                // Enter selection mode
                adapter.setSelectionMode(true);
                // Change button text to "Remove Selected"
                removeButton.setText("Remove Selected");
            }
        });

        // Handle the "Update" button click
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(StorageActivity.this, AddIngredientActivity.class);
            startActivity(intent);
        });
    }


        private void removeSelectedIngredients() {
            SparseBooleanArray selectedItems = adapter.getSelectedItems();
            for (int i = ingredientList.size() - 1; i >= 0; i--) {
                if (selectedItems.get(i)) {
                    Ingredient ingredient = ingredientList.get(i);
                    dbHelper.deleteIngredient(ingredient.getName());
                    ingredientList.remove(i);
                }
            }
            adapter.setSelectionMode(false);
            removeSelectedButton.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Selected ingredients removed.", Toast.LENGTH_SHORT).show();
        }
}
