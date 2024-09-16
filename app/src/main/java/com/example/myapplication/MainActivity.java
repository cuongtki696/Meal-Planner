package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

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
            // Show home content
            setContentView(R.layout.activity_main);
        });

        buttonStorage.setOnClickListener(v -> {
            // Navigate to StorageActivity
            Intent intent = new Intent(MainActivity.this, StorageActivity.class);
            startActivity(intent);
        });

        buttonShoppingList.setOnClickListener(v -> {
            // Navigate to ShoppingListActivity
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
        });
    }
}
