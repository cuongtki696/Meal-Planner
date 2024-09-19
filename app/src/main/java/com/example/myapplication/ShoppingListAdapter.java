package com.example.myapplication;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<ShoppingItem> shoppingList;
    private Context context;
    private boolean selectionMode = false;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public ShoppingListAdapter(Context context, List<ShoppingItem> shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingItem item = shoppingList.get(position);

        holder.name.setText(item.getName());
        holder.quantity.setText(item.getQuantity() + " " + item.getUnit());

        // Show or hide the checkbox based on selection mode
        holder.checkBox.setVisibility(selectionMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(selectedItems.get(position, false));

        holder.itemView.setOnClickListener(v -> {
            if (selectionMode) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
                selectedItems.put(position, holder.checkBox.isChecked());
            }
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedItems.put(position, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, quantity;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shopping_item_name);
            quantity = itemView.findViewById(R.id.shopping_item_quantity);
            checkBox = itemView.findViewById(R.id.shopping_item_checkbox);
        }
    }
}
