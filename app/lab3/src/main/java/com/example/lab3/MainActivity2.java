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
            b.putInt("a", 3);
            b.putInt("b", 2);
            i.putExtras(b);

            thirdLauncher.launch(i);
        });
    }
}