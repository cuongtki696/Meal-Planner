package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Load native library
    static {
        System.loadLibrary("native-lib");
    }

    // Native method to get the current date
    public native String getCurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        Button buttonPage1 = findViewById(R.id.button_page1);
        Button buttonPage2 = findViewById(R.id.button_page2);
        Button buttonPage3 = findViewById(R.id.button_page3);
        TextView footerDate = findViewById(R.id.footer_date);

        // Set footer date using native method
        footerDate.setText(getCurrentDate());

        // Add listeners for buttons (Example: just showing a toast message)
        buttonPage1.setOnClickListener(v -> {
            // Handle Page 1 button click
        });

        buttonPage2.setOnClickListener(v -> {
            // Handle Page 2 button click
        });

        buttonPage3.setOnClickListener(v -> {
            // Handle Page 3 button click
        });
    }
}