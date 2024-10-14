package meal.cuongtki696.org.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import meal.cuongtki696.org.databinding.ActivitySearchBinding;
import meal.cuongtki696.org.repo.db.Meal;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.ui.detail.MealDetailActivity;
import meal.cuongtki696.org.ui.home.MealAdapter;
import meal.cuongtki696.org.utils.AssetsHelper;

public class SearchActivity extends BaseActivity {
    private ActivitySearchBinding binding;
    private MealAdapter adapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MealAdapter();
        adapter.setOnItemClickListener((adapter, view, i) -> {
            Meal meal = adapter.getItem(i);
            MealDetailActivity.launch(this, meal.id);
        });

        binding.rvMeals.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMeals.setAdapter(adapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                List<Meal> meals = new ArrayList<>();
                if (!content.isEmpty()) {
                    meals = AssetsHelper.meals.stream().filter(meal -> meal.name.toLowerCase().contains(s.toString().toLowerCase())).collect(Collectors.toList());
                }
                adapter.submitList(meals);
            }
        });
    }
}
