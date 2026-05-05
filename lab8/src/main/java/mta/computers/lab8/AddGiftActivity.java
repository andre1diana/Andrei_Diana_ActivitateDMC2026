package mta.computers.lab8;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddGiftActivity extends AppCompatActivity {

    private EditText etMessage, etWeight;
    private Spinner spType;
    private CheckBox cbWrapped;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);

        db = AppDatabase.getInstance(this);

        etMessage = findViewById(R.id.etMessage);
        etWeight = findViewById(R.id.etWeight);
        spType = findViewById(R.id.spType);
        cbWrapped = findViewById(R.id.cbWrapped);
        Button btnSave = findViewById(R.id.btnSave);

        spType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Objects.values()));

        btnSave.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();
            String weightStr = etWeight.getText().toString().trim();

            if (message.isEmpty() || weightStr.isEmpty()) {
                Toast.makeText(this, "Completeaza toate campurile!", Toast.LENGTH_SHORT).show();
                return;
            }

            int weight = Integer.parseInt(weightStr);
            Objects type = (Objects) spType.getSelectedItem();
            boolean wrapped = cbWrapped.isChecked();

            Gift newGift = new Gift(message, weight, type.name(), wrapped);
            long id = db.giftDao().insert(newGift);

            if (id != -1) {
                Toast.makeText(this, "Salvat cu succes!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Eroare la salvare!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
