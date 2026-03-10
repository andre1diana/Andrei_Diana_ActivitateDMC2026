package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityLC";
    private ActivityResultLauncher<Intent> secondLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        secondLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String msg = result.getData().getStringExtra("back_msg");
                        int sum = result.getData().getIntExtra("back_sum", 0);
                        Toast.makeText(this, msg + " | sum=" + sum, Toast.LENGTH_LONG).show();
                    }
                }
        );

        Button btn = findViewById(R.id.btnGoSecond);
        btn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MainActivity2.class);
            secondLauncher.launch(i);
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart - Log.e");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume - Log.i");
    }

    @Override
    protected void onPause() {
        Log.w(TAG, "onPause - Log.w");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop - Log.v");
        super.onStop();
    }
}