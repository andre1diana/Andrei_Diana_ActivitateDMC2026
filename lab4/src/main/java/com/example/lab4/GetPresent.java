package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GetPresent extends AppCompatActivity {

    EditText etMessage, etWeight;
    DatePicker datePicker;
    CheckBox cbOpened;
    Spinner spObjectType;
    Button btnSaveCadou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_present);

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

            etMessage.setText(cadou.getMessage());
            etWeight.setText(String.valueOf(cadou.getWeight()));
            cbOpened.setChecked(cadou.isWrapped());

            spObjectType.setSelection(cadou.getObjectType().ordinal());

            if (cadou.getDeliveryDate() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(cadou.getDeliveryDate());

                datePicker.updateDate(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                );
            }
            // Schimbăm textul butonului pentru a indica editarea
            btnSaveCadou.setText("Salvează modificările");
        }

        btnSaveCadou.setOnClickListener(v -> saveCadou());
    }

    private void saveCadou() {
        String message = etMessage.getText().toString().trim();
        String weightText = etWeight.getText().toString().trim();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        Date deliveryDate = calendar.getTime();

        if (message.isEmpty()) {
            etMessage.setError("Introdu mesajul");
            return;
        }

        if (weightText.isEmpty()) {
            etWeight.setError("Introdu greutatea");
            return;
        }

        int weight;
        try {
            weight = Integer.parseInt(weightText);
        } catch (NumberFormatException e) {
            etWeight.setError("Greutatea trebuie sa fie numar");
            return;
        }

        boolean wrapped = cbOpened.isChecked();
        Objects objectType = (Objects) spObjectType.getSelectedItem();

        Cadou cadou = new Cadou(message, wrapped, weight, objectType, deliveryDate);

        Intent intent = new Intent();
        intent.putExtra("cadou", cadou);

        // Verificăm dacă am primit o poziție (înseamnă că edităm)
        if (getIntent().hasExtra("position")) {
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}