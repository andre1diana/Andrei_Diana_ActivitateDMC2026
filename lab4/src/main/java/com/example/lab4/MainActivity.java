package com.example.lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CADOU = 100;

    private Button btnAddCadou;
    private TextView tvMessage, tvOpened, tvWeight, tvObjectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCadou = findViewById(R.id.btnAddCadou);

        tvMessage = findViewById(R.id.tvMessage);
        tvOpened = findViewById(R.id.tvOpened);
        tvWeight = findViewById(R.id.tvWeight);
        tvObjectType = findViewById(R.id.tvObjectType);

        btnAddCadou.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GetPresent.class);
            startActivityForResult(intent, REQUEST_CODE_CADOU);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CADOU && resultCode == RESULT_OK && data != null) {
            Cadou cadou = (Cadou) data.getSerializableExtra("cadou");

            if (cadou != null) {
                tvMessage.setText("Mesaj: " + cadou.getMessage());
                tvOpened.setText("Deschis: " + (cadou.isWrapped() ? "Da" : "Nu"));
                tvWeight.setText("Greutate: " + cadou.getWeight() + " g");
                tvObjectType.setText("Tip obiect: " + cadou.getObjectType().name());
            }
        }
    }
}