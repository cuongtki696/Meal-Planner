package meal.cuongtki696.org.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import meal.cuongtki696.org.databinding.ActivityRegisterBinding;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.utils.AccountHelper;

public class RegisterActivity extends BaseActivity {
    private ActivityRegisterBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(v -> {
            register();
        });

    }

    private void register() {
        String username = binding.etAccount.getText().toString();
        String password = binding.etPassword.getText().toString();
        String repeatPassword = binding.etRepeatPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            Toast.makeText(this, "Account or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repeatPassword)) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (AccountHelper.isAccountExisted(username)) {
            Toast.makeText(this, "Account existed", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountHelper.registerAccount(username, password);
        Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
        finish();
    }
}
