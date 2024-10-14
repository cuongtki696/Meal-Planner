package meal.cuongtki696.org.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import meal.cuongtki696.org.databinding.ActivityModifyBinding;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.utils.AccountHelper;

public class ModifyActivity extends BaseActivity {
    private ActivityModifyBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, ModifyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConfirm.setOnClickListener(v -> {
            modify();
        });
    }

    private void modify() {
        String currentPassword = binding.etCurrentPassword.getText().toString();
        String newPassword = binding.etPassword.getText().toString();
        String repeatNewPassword = binding.etRepeatPassword.getText().toString();

        if (!AccountHelper.getAccountPassword(AccountHelper.getActiveAccount()).equals(currentPassword)) {
            Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(repeatNewPassword)) {
            Toast.makeText(this, "New password not match", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountHelper.registerAccount(AccountHelper.getActiveAccount(), newPassword);
        Toast.makeText(this, "Modify success", Toast.LENGTH_SHORT).show();
        finish();
    }
}
