package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // Set up the navigation bar
        Button homeButton = findViewById(R.id.button_home);
        Button storageButton = findViewById(R.id.button_storage);
        Button shoppingListButton = findViewById(R.id.button_shopping_list);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        storageButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingListActivity.this, StorageActivity.class);
            startActivity(intent);
        });

        shoppingListButton.setOnClickListener(v -> {
            // Current Activity is ShoppingListActivity, do nothing
        });

        // Set up RecyclerView or other UI elements for the shopping list
    }
}
