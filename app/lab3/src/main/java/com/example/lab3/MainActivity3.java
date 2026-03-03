package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {
    private int a, b;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            msg = extras.getString("msg", "(fara mesaj)");
            a = extras.getInt("a", 0);
            b = extras.getInt("b", 0);

            Toast.makeText(this, msg + " a=" + a + " b=" + b, Toast.LENGTH_LONG).show();
        }

        Button btn = findViewById(R.id.btnSendBack);
        btn.setOnClickListener(v -> {
            int sum = a + b;

            Intent back = new Intent();
            back.putExtra("back_msg", "Mesaj din ThirdActivity");
            back.putExtra("back_sum", sum);

            setResult(RESULT_OK, back);
            finish();
        });
    }
}