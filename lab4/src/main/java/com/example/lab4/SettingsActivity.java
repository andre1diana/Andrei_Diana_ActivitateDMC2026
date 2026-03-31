package com.example.lab4;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText etSize;
    private Spinner spColor;
    private Button btnSave;

    private String[] colors = {"Negru", "Rosu", "Albastru", "Verde"};
    private String[] colorValues = {"#000000", "#FF0000", "#0000FF", "#008000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etSize = findViewById(R.id.etSize);
        spColor = findViewById(R.id.spColor);
        btnSave = findViewById(R.id.btnSaveSettings);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor.setAdapter(adapter);

        // Incarca setarile salvate
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        int savedSize = prefs.getInt("textSize", 18);
        String savedColor = prefs.getString("textColor", "#000000");

        etSize.setText(String.valueOf(savedSize));
        
        // Gaseste indexul culorii salvate
        for (int i = 0; i < colorValues.length; i++) {
            if (colorValues[i].equals(savedColor)) {
                spColor.setSelection(i);
                break;
            }
        }

        btnSave.setOnClickListener(v -> saveSettings());
    }

    private void saveSettings() {
        String sizeStr = etSize.getText().toString().trim();
        if (sizeStr.isEmpty()) {
            etSize.setError("Introdu dimensiunea");
            return;
        }

        int size = Integer.parseInt(sizeStr);
        int selectedIndex = spColor.getSelectedItemPosition();
        String colorValue = colorValues[selectedIndex];

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("textSize", size);
        editor.putString("textColor", colorValue);

        editor.apply();

        Toast.makeText(this, "Setari salvate!", Toast.LENGTH_SHORT).show();
        finish();
    }
}