package meal.cuongtki696.org.ui.storage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import meal.cuongtki696.org.databinding.ActivityStorageListBinding;  // Make sure to have a corresponding binding
import meal.cuongtki696.org.ui.base.BaseActivity;  // If needed, you can extend from BaseActivity
import meal.cuongtki696.org.utils.AccountHelper;

public class StorageListActivity extends BaseActivity {
    private ActivityStorageListBinding binding;
    private StorageListAdapter adapter;
    private List<StorageItem> items = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static void launch(Context context) {
        context.startActivity(new Intent(context, StorageListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStorageListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup RecyclerView with LayoutManager
        binding.rvStorageItems.setLayoutManager(new LinearLayoutManager(this));

        // Fetch storage items from AccountHelper
        Set<String> storageSet = AccountHelper.getStorage();



        for (String item : storageSet) {
            try {
                // Split the item into the "name (quantity unit)" part and the expiration date part
                String[] parts = item.split(":", 2);  // Split into two parts at the first colon

                String nameWithQuantity = parts[0].trim();  // The part containing "name (quantity unit)"
                String expirationDateStr = parts[1].trim();  // The expiration date (e.g., "2024-12-12")

                // Extract the name, quantity, and unit from "name (quantity unit)"
                int startOfParen = nameWithQuantity.indexOf("(");
                int endOfParen = nameWithQuantity.indexOf(")");

                if (startOfParen == -1 || endOfParen == -1) {
                    throw new IllegalArgumentException("Invalid format: missing parentheses in " + nameWithQuantity);
                }

                // Extract the name part (before the parentheses)
                String name = nameWithQuantity.substring(0, startOfParen).trim();

                // Extract the "quantity unit" part inside parentheses
                String quantityAndUnit = nameWithQuantity.substring(startOfParen + 1, endOfParen).trim();
                String[] quantityUnitParts = quantityAndUnit.split(" ");  // Split by space to separate quantity and unit

                // Parse the quantity and unit
                int quantity = Integer.parseInt(quantityUnitParts[0]);  // First part is the quantity
                String unit = quantityUnitParts[1];  // Second part is the unit (e.g., "g" or "kg")

                // Parse the expiration date
                Date expirationDate = sdf.parse(expirationDateStr);

                // Create and add the StorageItem
                StorageItem storageItem = new StorageItem(name, quantity, unit, expirationDate);
                items.add(storageItem);
            } catch (NumberFormatException e) {
                Log.e("StorageListActivity", "Error parsing number for storage item: " + item, e);
            } catch (IllegalArgumentException e) {
                Log.e("StorageListActivity", "Illegal argument in storage item: " + item, e);
            } catch (ParseException e) {
                Log.e("StorageListActivity", "Error parsing date for storage item: " + item, e);
            }
        }

        // Handle empty list visibility
        if (items == null || items.isEmpty()) {
            binding.tvEmptyStorage.setVisibility(View.VISIBLE);
            binding.rvStorageItems.setVisibility(View.GONE);
        } else {
            binding.tvEmptyStorage.setVisibility(View.GONE);
            adapter = new StorageListAdapter(this, items);
            binding.rvStorageItems.setAdapter(adapter);
        }

        // Handle Remove button functionality
        binding.btnRemoveStorage.setOnClickListener(v -> {
            if (adapter.isSelectionMode()) {
                removeSelectedItem();
                binding.btnRemoveStorage.setText("Remove");
                adapter.setSelectionMode(false);
            } else {
                adapter.setSelectionMode(true);
                binding.btnRemoveStorage.setText("Remove Selected");
            }
        });

        // Handle Update button functionality
        binding.btnUpdateStorage.setOnClickListener(v -> {
            StorageAddActivity.launch(this);  // Assuming StorageAddActivity exists
        });
        List<StorageItem> expiringItems = getItemsExpiringSoon(3);

        // If there are items expiring soon, show a warning
        if (!expiringItems.isEmpty()) {
            showExpirationWarning(expiringItems);
        }
    }
    private void removeSelectedItem() {
        SparseBooleanArray selectedItems = adapter.getSelectedItems();

        // Iterate backwards through the list to remove selected items
        for (int i = items.size() - 1; i >= 0; i--) {
            if (selectedItems.get(i)) {
                StorageItem item = items.get(i);
                String formattedItem = item.getName() + ":" + item.getQuantity() + ":" + sdf.format(item.getExpirationDate());
                AccountHelper.removeStorage(formattedItem);

                items.remove(i);
            }
        }
        adapter.setSelectionMode(false);
        adapter.notifyDataSetChanged();
        binding.btnRemoveStorage.setVisibility(View.GONE);
        Toast.makeText(this, "Selected items removed.", Toast.LENGTH_SHORT).show();
    }
    private void showExpirationWarning(List<StorageItem> expiringItems) {
        StringBuilder warningMessage = new StringBuilder("Warning! The following items are about to expire:\n");

        // Build a list of expiring items
        for (StorageItem item : expiringItems) {
            warningMessage.append(item.getName())
                    .append(" - Expires on: ")
                    .append(new SimpleDateFormat("yyyy-MM-dd").format(item.getExpirationDate()))
                    .append("\n");
        }

        // Display an alert dialog or a Toast message with the warning
        new AlertDialog.Builder(this)
                .setTitle("Expiration Warning")
                .setMessage(warningMessage.toString())
                .setPositiveButton("OK", null)
                .show();
    }
    public List<StorageItem> getItemsExpiringSoon(int daysThreshold) {
        List<StorageItem> expiringItems = new ArrayList<>();

        // Get the current date
        Date currentDate = new Date();

        // Iterate through storage items and check their expiration dates
        for (StorageItem item : items) {
            long diffInMillies = item.getExpirationDate().getTime() - currentDate.getTime();
            long daysUntilExpiration = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            // If the item expires within the threshold (e.g., 3 days), add to the expiring list
            if (daysUntilExpiration <= daysThreshold && daysUntilExpiration >= 0) {
                expiringItems.add(item);
            }
        }

        return expiringItems;
    }


}
