package meal.cuongtki696.org.ui.storage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import meal.cuongtki696.org.R;
import meal.cuongtki696.org.utils.AccountHelper;

public class StorageAddActivity extends AppCompatActivity {

    private EditText etName, etQuantity, etUnit, etExpiration;
    private Button btnAdd;

    public static void launch(Context context) {
        Intent intent = new Intent(context, StorageAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_add);  // Ensure this layout exists

        // Initialize the views
        etName = findViewById(R.id.et_storage_item_name);
        etQuantity = findViewById(R.id.et_storage_item_quantity);
        etUnit = findViewById(R.id.et_storage_item_unit);
        etExpiration = findViewById(R.id.et_storage_item_expiration);
        btnAdd = findViewById(R.id.btn_add_storage_item);

        // Set click listener for the add button
        btnAdd.setOnClickListener(v -> addStorageItem());
    }

    private void addStorageItem() {
        // Get the input values from the user
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expirationDate = etExpiration.getText().toString().trim();

        // Validate the input
        if (TextUtils.isEmpty(name)) {
            etName.setError("Item name is required");
            return;
        }

        if (TextUtils.isEmpty(quantityStr)) {
            etQuantity.setError("Quantity is required");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            etQuantity.setError("Invalid quantity");
            return;
        }

        if (TextUtils.isEmpty(unit)) {
            etUnit.setError("Unit is required");
            return;
        }

        if (TextUtils.isEmpty(expirationDate)) {
            etExpiration.setError("Expiration date is required");
            return;
        }

        // Add the storage item using AccountHelper (which stores the item with expiration date)
        boolean isAdded = AccountHelper.addStorageItem(name + " (" + quantity + " " + unit + ")", expirationDate);

        // Show a message based on the result
        if (isAdded) {
            Toast.makeText(this, "Item added to storage", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity after the item is added
        } else {
            Toast.makeText(this, "Failed to add item to storage", Toast.LENGTH_SHORT).show();
        }
    }
}
