package meal.cuongtki696.org.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import meal.cuongtki696.org.R;
import meal.cuongtki696.org.databinding.FragmentHomeBinding;
import meal.cuongtki696.org.repo.db.Meal;
import meal.cuongtki696.org.ui.detail.MealDetailActivity;
import meal.cuongtki696.org.ui.search.SearchActivity;
import meal.cuongtki696.org.utils.AssetsHelper;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BannerAdapter bannerAdapter;
    private MealAdapter mealAdapter;

    private BottomSheetDialog filterDialog;

    private HashMap<String, String> filters = new HashMap<String, String>() {{
        put("country", "all");
        put("type", "all");
        put("allergy", "all");
        put("nutrient", "all");
    }};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.searchBar.setOnClickListener(v -> {
            SearchActivity.launch(requireContext());
        });

        bannerAdapter = new BannerAdapter();
        mealAdapter = new MealAdapter();
        bannerAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn);
        mealAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn);
        bannerAdapter.setOnItemClickListener((adapter, view, i) -> {
            Meal meal = adapter.getItem(i);
            MealDetailActivity.launch(requireContext(), meal.id);
        });
        mealAdapter.setOnItemClickListener((adapter, view, i) -> {
            Meal meal = adapter.getItem(i);
            MealDetailActivity.launch(requireContext(), meal.id);
        });

        binding.rvBanner.setLayoutManager(new CarouselLayoutManager());
        binding.rvBanner.setAdapter(bannerAdapter);

        binding.rvMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvMeals.setAdapter(mealAdapter);

        binding.ivFilter.setOnClickListener(v -> {
            showFilterDialog();
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            refreshData();
        }, 500);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void refreshData() {
        bannerAdapter.submitList(AssetsHelper.meals);

        List<Meal> filter = AssetsHelper.meals.stream()
                .filter(meal -> {
                    if (!filters.get("country").equalsIgnoreCase("All")) {
                        return filters.get("country").equalsIgnoreCase(meal.country);
                    }
                    return true;
                }).filter(meal -> {
                    if (!filters.get("type").equalsIgnoreCase("All")) {
                        return filters.get("type").equalsIgnoreCase(meal.category);
                    }
                    return true;
                }).filter(meal -> {
                    if (!filters.get("allergy").equalsIgnoreCase("All")) {
                        return filters.get("allergy").equalsIgnoreCase(meal.allergy);
                    }
                    return true;
                }).filter(meal -> {
                    if (!filters.get("nutrient").equalsIgnoreCase("All")) {
                        return meal.nutrients.contains(filters.get("nutrient"));
                    }
                    return true;
                })
                .collect(Collectors.toList());
        mealAdapter.submitList(filter);
    }

    private void showFilterDialog() {
        filterDialog = new BottomSheetDialog(requireContext());
        filterDialog.setContentView(R.layout.dialog_filter);
        filterDialog.setDismissWithAnimation(true);
        filterDialog.show();

        ChipGroup countryChipGroup = filterDialog.findViewById(R.id.cg_country);
        ChipGroup typeChipGroup = filterDialog.findViewById(R.id.cg_type);
        ChipGroup allergyChipGroup = filterDialog.findViewById(R.id.cg_allergy);
        ChipGroup nutrientChipGroup = filterDialog.findViewById(R.id.cg_nutrient);

        for (String text : getResources().getStringArray(R.array.Country)) {
            Chip chip = (Chip) filterDialog.getLayoutInflater().inflate(R.layout.item_filter, countryChipGroup, false);
            chip.setText(text);
            chip.setChecked(text.equalsIgnoreCase(filters.get("country")));
            countryChipGroup.addView(chip);
        }
        countryChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (Integer checkedId : checkedIds) {
                Chip selectedChip = filterDialog.findViewById(checkedId);
                filters.put("country", selectedChip.getText().toString());
            }
        });

        for (String text : getResources().getStringArray(R.array.Type)) {
            Chip chip = (Chip) filterDialog.getLayoutInflater().inflate(R.layout.item_filter, typeChipGroup, false);
            chip.setText(text);
            chip.setChecked(text.equalsIgnoreCase(filters.get("type")));
            typeChipGroup.addView(chip);
        }
        typeChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (Integer checkedId : checkedIds) {
                Chip selectedChip = filterDialog.findViewById(checkedId);
                filters.put("type", selectedChip.getText().toString());
            }
        });

        for (String text : getResources().getStringArray(R.array.Allergy)) {
            Chip chip = (Chip) filterDialog.getLayoutInflater().inflate(R.layout.item_filter, allergyChipGroup, false);
            chip.setText(text);
            chip.setChecked(text.equalsIgnoreCase(filters.get("allergy")));
            allergyChipGroup.addView(chip);
        }
        allergyChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (Integer checkedId : checkedIds) {
                Chip selectedChip = filterDialog.findViewById(checkedId);
                filters.put("allergy", selectedChip.getText().toString());
            }
        });

        for (String text : getResources().getStringArray(R.array.Nutrient)) {
            Chip chip = (Chip) filterDialog.getLayoutInflater().inflate(R.layout.item_filter, nutrientChipGroup, false);
            chip.setText(text);
            chip.setChecked(text.equalsIgnoreCase(filters.get("nutrient")));
            nutrientChipGroup.addView(chip);
        }
        nutrientChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            for (Integer checkedId : checkedIds) {
                Chip selectedChip = filterDialog.findViewById(checkedId);
                filters.put("nutrient", selectedChip.getText().toString());
            }
        });

        filterDialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            filterDialog.dismiss();
            refreshData();
        });
    }
}