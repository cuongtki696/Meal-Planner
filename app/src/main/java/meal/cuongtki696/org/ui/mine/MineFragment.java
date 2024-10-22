package meal.cuongtki696.org.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import meal.cuongtki696.org.databinding.FragmentMineBinding;
import meal.cuongtki696.org.ui.search.search.HistoryActivity;
import meal.cuongtki696.org.ui.shopping.ShoppingListActivity;
import meal.cuongtki696.org.ui.storage.StorageListActivity;
import meal.cuongtki696.org.utils.AccountHelper;

public class MineFragment extends Fragment {
    private FragmentMineBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(requireContext()).asBitmap().load("file:///android_asset/avatars/avatar01.png").into(binding.ivAvatar);
        binding.tvUsername.setText(AccountHelper.getActiveAccount());
        binding.tvHistory.setOnClickListener(v -> {
            HistoryActivity.launch(requireContext());
        });
        binding.tvFoodBasket.setOnClickListener(v -> {
            ShoppingListActivity.launch(requireContext());
        });
        binding.tvStorageList.setOnClickListener(v -> {
            StorageListActivity.launch(requireContext());  // Launch StorageListActivity
        });
        binding.btnLogout.setOnClickListener(v -> {
            AccountHelper.saveActiveAccount(null);
            LoginActivity.launch(requireContext());
            requireActivity().finish();
        });

        binding.tvModifyPassword.setOnClickListener(v -> {
            ModifyActivity.launch(requireContext());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}