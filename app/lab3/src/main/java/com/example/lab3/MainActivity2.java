package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private ActivityResultLauncher<Intent> thirdLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        thirdLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        setResult(RESULT_OK, result.getData());
                        finish();
                    }
                }
        );

        Button btn = findViewById(R.id.btnGoThird);
        btn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity2.this, MainActivity3.class);

            Bundle b = new Bundle();
            b.putString("msg", "Salut din SecondActivity!");
            b.putInt("a", 7);
            b.putInt("b", 13);
            i.putExtras(b);

            thirdLauncher.launch(i);
        });
    }

    private static final String TAG = "SecondActivityLC";

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart - Log.e");
        Log.w(TAG, "onStart - Log.w");
        Log.d(TAG, "onStart - Log.d");
        Log.i(TAG, "onStart - Log.i");
        Log.v(TAG, "onStart - Log.v");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume - Log.e");
        Log.w(TAG, "onResume - Log.w");
        Log.d(TAG, "onResume - Log.d");
        Log.i(TAG, "onResume - Log.i");
        Log.v(TAG, "onResume - Log.v");
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause - Log.e");
        Log.w(TAG, "onPause - Log.w");
        Log.d(TAG, "onPause - Log.d");
        Log.i(TAG, "onPause - Log.i");
        Log.v(TAG, "onPause - Log.v");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop - Log.e");
        Log.w(TAG, "onStop - Log.w");
        Log.d(TAG, "onStop - Log.d");
        Log.i(TAG, "onStop - Log.i");
        Log.v(TAG, "onStop - Log.v");
        super.onStop();
    }
}