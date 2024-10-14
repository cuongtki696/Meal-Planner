package meal.cuongtki696.org.ui.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import meal.cuongtki696.org.databinding.ActivityShoppingAddBinding;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.utils.AccountHelper;

public class ShoppingAddActivity extends BaseActivity {
    private ActivityShoppingAddBinding binding;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ShoppingAddActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(v -> {
            String itemName = binding.etName.getText().toString();
            String itemQuantityStr = binding.etQuantity.getText().toString();
            String unit = binding.etUnit.getText().toString();

            if (itemName.isEmpty() || itemQuantityStr.isEmpty() || unit.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = capitalizeWords(itemName);
            int quantity = Integer.parseInt(itemQuantityStr);

            AccountHelper.addShopping(name + "-" + quantity + "-" + unit);

            Toast.makeText(this, "Item added to shopping list successfully!", Toast.LENGTH_SHORT).show();
            finish();
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
