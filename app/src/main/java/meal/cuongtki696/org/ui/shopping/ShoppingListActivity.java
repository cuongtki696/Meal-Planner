package meal.cuongtki696.org.ui.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import meal.cuongtki696.org.databinding.ActivityShoppingListBinding;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.utils.AccountHelper;

public class ShoppingListActivity extends BaseActivity {
    private ActivityShoppingListBinding binding;

    private ShoppingListAdapter adapter;
    private List<ShoppingItem> items = new ArrayList<>();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ShoppingListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));

        items = AccountHelper.getShopping().stream().map(item -> {
            String[] parts = item.replace(AccountHelper.getActiveAccount(), "").split("-");
            return new ShoppingItem(parts[0], Integer.parseInt(parts[1]), parts[2]);
        }).collect(Collectors.toList());

        if (items == null || items.isEmpty()) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvItems.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            adapter = new ShoppingListAdapter(this, items);
            binding.rvItems.setAdapter(adapter);
        }

        binding.btnRemove.setOnClickListener(v -> {
            if (adapter.isSelectionMode()) {
                removeSelectedItem();
                binding.btnRemove.setText("Remove");
                adapter.setSelectionMode(false);
            } else {
                adapter.setSelectionMode(true);
                binding.btnRemove.setText("Remove Selected");
            }
        });

        binding.btnUpdate.setOnClickListener(v -> {
            ShoppingAddActivity.launch(this);
        });
    }

    private void removeSelectedItem() {
        SparseBooleanArray selectedItems = adapter.getSelectedItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            if (selectedItems.get(i)) {
                ShoppingItem Item = items.get(i);
                AccountHelper.removeShopping(Item.getName() + "-" + Item.getQuantity() + "-" + Item.getUnit());
                items.remove(i);
            }
        }
        adapter.setSelectionMode(false);
        adapter.notifyDataSetChanged();

        binding.btnRemove.setVisibility(View.GONE);

        Toast.makeText(this, "Selected Item removed.", Toast.LENGTH_SHORT).show();
    }
}
