package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

public class GetPresent extends AppCompatActivity {

    private EditText etMessage, etWeight;
    private DatePicker datePicker;
    private CheckBox cbOpened;
    private Spinner spObjectType;
    private Button btnSaveCadou;
    private TextView tvTitle, tvLMessage, tvLWeight, tvLDate, tvLType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_present);

        // Initializare view-uri
        tvTitle = findViewById(R.id.tvTitle);
        etMessage = findViewById(R.id.etMessage);
        etWeight = findViewById(R.id.etWeight);
        datePicker = findViewById(R.id.datePicker);
        cbOpened = findViewById(R.id.cbOpened);
        spObjectType = findViewById(R.id.spObjectType);
        btnSaveCadou = findViewById(R.id.btnSaveCadou);


        // Configurare Spinner
        ArrayAdapter<Objects> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Objects.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spObjectType.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent.hasExtra("cadou")) {
            Cadou cadou = intent.getParcelableExtra("cadou");
            if (cadou != null) {
                etMessage.setText(cadou.getMessage());
                etWeight.setText(String.valueOf(cadou.getWeight()));
                cbOpened.setChecked(cadou.isWrapped());
                spObjectType.setSelection(cadou.getObjectType().ordinal());

                if (cadou.getDeliveryDate() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(cadou.getDeliveryDate());
                    datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                }
                btnSaveCadou.setText("Salveaza modificarile");
            }
        }

        btnSaveCadou.setOnClickListener(v -> saveCadou());
    }

    @Override
    protected void onResume() {
        super.onResume();
        applySettings();
    }

    private void applySettings() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        int textSize = prefs.getInt("textSize", 18);
        String textColorStr = prefs.getString("textColor", "#000000");
        int textColor = Color.parseColor(textColorStr);


        tvTitle.setTextSize(textSize + 4);
        tvTitle.setTextColor(textColor);

        etMessage.setTextSize(textSize);
        etMessage.setTextColor(textColor);

        etWeight.setTextSize(textSize);
        etWeight.setTextColor(textColor);

        cbOpened.setTextSize(textSize);
        cbOpened.setTextColor(textColor);
    }

    private void saveToFile(Cadou cadou) {
        try {
            FileOutputStream fos = openFileOutput("cadouri.txt", MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cadou);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCadou() {
        String message = etMessage.getText().toString().trim();
        String weightText = etWeight.getText().toString().trim();
        
        if (message.isEmpty() || weightText.isEmpty()) {
            Toast.makeText(this, "Completeaza toate campurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        Date deliveryDate = calendar.getTime();

        int weight = Integer.parseInt(weightText);
        boolean wrapped = cbOpened.isChecked();
        Objects objectType = (Objects) spObjectType.getSelectedItem();

        Cadou cadou = new Cadou(message, wrapped, weight, objectType, deliveryDate);
        saveToFile(cadou);

        Intent intent = new Intent();
        intent.putExtra("cadou", (android.os.Parcelable) cadou);
        if (getIntent().hasExtra("position")) {
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}