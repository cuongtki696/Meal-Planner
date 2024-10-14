package meal.cuongtki696.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import meal.cuongtki696.org.databinding.ActivityMainBinding;
import meal.cuongtki696.org.ui.base.BaseActivity;
import meal.cuongtki696.org.ui.mine.LoginActivity;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}