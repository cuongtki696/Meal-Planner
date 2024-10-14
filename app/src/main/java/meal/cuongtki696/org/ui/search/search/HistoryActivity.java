package meal.cuongtki696.org.ui.search.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import meal.cuongtki696.org.databinding.ActivityHistoryBinding;
import meal.cuongtki696.org.repo.db.Meal;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.ui.detail.MealDetailActivity;
import meal.cuongtki696.org.ui.home.MealAdapter;
import meal.cuongtki696.org.utils.AccountHelper;
import meal.cuongtki696.org.utils.AssetsHelper;

public class HistoryActivity extends BaseActivity {
    private ActivityHistoryBinding binding;
    private MealAdapter adapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MealAdapter();
        adapter.setOnItemClickListener((adapter, view, i) -> {
            Meal meal = adapter.getItem(i);
            MealDetailActivity.launch(this, meal.id);
        });

        binding.rvMeals.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMeals.setAdapter(adapter);

        List<Meal> meals = AccountHelper.getHistory()
                .stream()
                .map(id -> AssetsHelper.meals.stream().filter(meal -> meal.id == Integer.parseInt(id)).findFirst().orElse(null))
                .collect(Collectors.toList());
        Collections.reverse(meals);
        adapter.submitList(meals);
    }
}
