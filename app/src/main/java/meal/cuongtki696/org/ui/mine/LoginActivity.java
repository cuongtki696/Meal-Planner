package meal.cuongtki696.org.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import meal.cuongtki696.org.MainActivity;
import meal.cuongtki696.org.databinding.ActivityLoginBinding;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.utils.AccountHelper;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            login();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            RegisterActivity.launch(this);
        });

        if (AccountHelper.getActiveAccount() != null) {
            MainActivity.launch(this);
            finish();
        }
    }

    private void login() {
        String username = binding.etAccount.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Account or password is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AccountHelper.isAccountExisted(username)) {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AccountHelper.getAccountPassword(username).equals(password)) {
            Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountHelper.saveActiveAccount(username);
        MainActivity.launch(this);
        finish();
    }
}
