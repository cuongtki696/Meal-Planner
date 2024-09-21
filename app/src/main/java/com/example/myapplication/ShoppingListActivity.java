package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ShoppingListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private List<ShoppingItem> ItemList;
    private TextView ShoppingListEmptyMessage;
    private Button removeSelectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.shopping_list_recycler_view);
        ShoppingListEmptyMessage = findViewById(R.id.shopping_list_empty_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get Item from Shopping List
        ItemList = dbHelper.getAllItem();
        if (ItemList == null || ItemList.isEmpty()) {
            ShoppingListEmptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            ShoppingListEmptyMessage.setVisibility(View.GONE);
            adapter = new ShoppingListAdapter(this, ItemList);
            recyclerView.setAdapter(adapter);
        }

        // Set up the navigation bar
        Button homeButton = findViewById(R.id.button_home);
        Button storageButton = findViewById(R.id.button_storage);
        Button shoppingListButton = findViewById(R.id.button_shopping_list);
        Button removeButton = findViewById(R.id.button_remove_shopping);
        Button updateButton = findViewById(R.id.button_update_shopping);

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

        // Set up the Remove button
        removeButton.setOnClickListener(v -> {
            if (adapter.isSelectionMode()) {
                // If in selection mode, perform the removal
                removeSelectedItem();
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
            Intent intent = new Intent(ShoppingListActivity.this, AddShoppingListItemActivity.class);
            startActivity(intent);
        });
    }
    private void removeSelectedItem() {
        SparseBooleanArray selectedItems = adapter.getSelectedItems();
        for (int i = ItemList.size() - 1; i >= 0; i--) {
            if (selectedItems.get(i)) {
                ShoppingItem Item = ItemList.get(i);
                dbHelper.deleteIngredient(Item.getName());
                ItemList.remove(i);
            }
        }
        adapter.setSelectionMode(false);
        removeSelectedButton.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Selected Item removed.", Toast.LENGTH_SHORT).show();
    }
}
