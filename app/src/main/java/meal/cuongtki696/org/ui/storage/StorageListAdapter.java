package meal.cuongtki696.org.ui.storage;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import meal.cuongtki696.org.R;

public class StorageListAdapter extends RecyclerView.Adapter<StorageListAdapter.StorageViewHolder> {

    private Context context;
    private List<StorageItem> storageItems;
    private boolean selectionMode = false;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public StorageListAdapter(Context context, List<StorageItem> storageItems) {
        this.context = context;
        this.storageItems = storageItems;
    }

    // Method to check if in selection mode
    public boolean isSelectionMode() {
        return selectionMode;
    }

    // Method to set selection mode
    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
        selectedItems.clear();
        notifyDataSetChanged();  // Refresh the list to show checkboxes if in selection mode
    }

    // Method to get the selected items
    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }

    // ViewHolder class that holds the item layout
    public static class StorageViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView itemName, itemQuantity, itemExpiration;

        public StorageViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_storage_item);
            itemName = itemView.findViewById(R.id.tv_storage_item_name);
            itemQuantity = itemView.findViewById(R.id.tv_storage_item_quantity);
            itemExpiration = itemView.findViewById(R.id.tv_storage_item_expiration);
        }
    }

    @NonNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_storage, parent, false);
        return new StorageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageViewHolder holder, int position) {
        // Get the StorageItem object at the current position
        StorageItem storageItem = storageItems.get(position);

        // Set the item name, quantity, and expiration date to the TextViews
        holder.itemName.setText(storageItem.getName());  // Item name
        holder.itemQuantity.setText("Quantity: " + storageItem.getQuantity() + " " + storageItem.getUnit());  // Item quantity with unit
        holder.itemExpiration.setText("Expires on: " + sdf.format(storageItem.getExpirationDate()));  // Expiration date formatted

        // Show or hide the checkbox based on selection mode
        holder.checkBox.setVisibility(selectionMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(selectedItems.get(position, false));

        // Handle click on the item (selecting/unselecting)
        holder.itemView.setOnClickListener(v -> {
            if (selectionMode) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
                selectedItems.put(position, holder.checkBox.isChecked());
            }
        });

        // Handle changes to the checkbox state
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedItems.put(position, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return storageItems.size();
    }
}
