package meal.cuongtki696.org.ui.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter4.BaseQuickAdapter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import meal.cuongtki696.org.databinding.FragmentCollectBinding;
import meal.cuongtki696.org.repo.db.Meal;
import meal.cuongtki696.org.ui.detail.MealDetailActivity;
import meal.cuongtki696.org.ui.home.MealAdapter;
import meal.cuongtki696.org.utils.AccountHelper;
import meal.cuongtki696.org.utils.AssetsHelper;

public class CollectFragment extends Fragment {

    private FragmentCollectBinding binding;
    private MealAdapter mealAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCollectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mealAdapter = new MealAdapter();
        mealAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn);
        mealAdapter.setOnItemClickListener((adapter, view, i) -> {
            Meal meal = adapter.getItem(i);
            MealDetailActivity.launch(requireContext(), meal.id);
        });

        binding.rvMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMeals.setAdapter(mealAdapter);

        List<Meal> meals = AccountHelper.getCollect()
                .stream()
                .map(id -> AssetsHelper.meals.stream().filter(meal -> meal.id == Integer.parseInt(id)).findFirst().orElse(null))
                .collect(Collectors.toList());
        Collections.reverse(meals);
        mealAdapter.submitList(meals);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}