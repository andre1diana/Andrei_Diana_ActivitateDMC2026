package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class GetPresent extends AppCompatActivity {

    EditText etMessage, etWeight;
    CheckBox cbOpened;
    Spinner spObjectType;
    Button btnSaveCadou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_present);

        etMessage = findViewById(R.id.etMessage);
        etWeight = findViewById(R.id.etWeight);
        cbOpened = findViewById(R.id.cbOpened);
        spObjectType = findViewById(R.id.spObjectType);
        btnSaveCadou = findViewById(R.id.btnSaveCadou);

        ArrayAdapter<Objects> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Objects.values()
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spObjectType.setAdapter(adapter);

        btnSaveCadou.setOnClickListener(v -> saveCadou());
    }

    private void saveCadou() {

        String message = etMessage.getText().toString().trim();
        String weightText = etWeight.getText().toString().trim();

        if (message.isEmpty()) {
            etMessage.setError("Introdu mesajul");
            return;
        }

        if (weightText.isEmpty()) {
            etWeight.setError("Introdu greutatea");
            return;
        }

        int weight = Integer.parseInt(weightText);
        boolean opened = cbOpened.isChecked();
        Objects objectType = (Objects) spObjectType.getSelectedItem();

        Cadou cadou = new Cadou(message, opened, weight, objectType);

        Intent intent = new Intent();
        intent.putExtra("cadou", cadou);

        setResult(RESULT_OK, intent);
        finish();
    }
}