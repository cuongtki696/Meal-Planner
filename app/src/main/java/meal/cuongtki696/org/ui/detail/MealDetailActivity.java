package meal.cuongtki696.org.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import meal.cuongtki696.org.R;
import meal.cuongtki696.org.databinding.ActivityMealDetailBinding;
import meal.cuongtki696.org.repo.db.Meal;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.utils.AccountHelper;
import meal.cuongtki696.org.utils.AssetsHelper;

public class MealDetailActivity extends BaseActivity {
    private ActivityMealDetailBinding binding;
    private Meal meal;

    public static void launch(Context context, int mealId) {
        AccountHelper.addHistory(String.valueOf(mealId));

        Intent intent = new Intent(context, MealDetailActivity.class);
        intent.putExtra("meal_id", mealId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMealDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int mealId = getIntent().getIntExtra("meal_id", -1);
        meal = AssetsHelper.meals.stream().filter(meal1 -> meal1.id == mealId).findFirst().orElse(null);

        getSupportActionBar().setTitle(meal.name);
        Glide.with(this).load(meal.image).into(binding.ivImage);

        binding.tvIngredients.setText(String.join("\n", meal.ingredients));
        binding.tvNutrients.setText(String.format("Protein: %sg\nFat: %sg\nCarbs: %sg\nMicro Nutrients:%s ", meal.protein, meal.fat, meal.carbs, String.join(", ", meal.nutrients)));
        binding.tvInstructions.setText(meal.instructions);
        binding.tvProcessSteps.setText(Html.fromHtml(String.join("\n", meal.processSteps)));
        binding.tvVideoLink.setText(meal.videoLink);
        binding.tvFitnessAdvice.setText(meal.fitnessAdvice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        refreshCollect();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_collect) {
            handleCollect();
            refreshCollect();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleCollect() {
        if (AccountHelper.getCollect().contains(String.valueOf(meal.id))) {
            AccountHelper.removeCollect(String.valueOf(meal.id));
            Toast.makeText(this, "Remove from collect", Toast.LENGTH_SHORT).show();
        } else {
            AccountHelper.addCollect(String.valueOf(meal.id));
            Toast.makeText(this, "Add to collect", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCollect() {
        if (AccountHelper.getCollect().contains(String.valueOf(meal.id))) {
            binding.toolbar.getMenu().findItem(R.id.action_collect).setIcon(R.drawable.ic_favorite_filled);
        } else {
            binding.toolbar.getMenu().findItem(R.id.action_collect).setIcon(R.drawable.ic_favorite);
        }
    }
}
