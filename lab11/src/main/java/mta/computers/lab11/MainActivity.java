package mta.computers.lab11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etVal1, etVal2, etVal3;
    private Button btnShowChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        etVal1 = findViewById(R.id.etVal1);
        etVal2 = findViewById(R.id.etVal2);
        etVal3 = findViewById(R.id.etVal3);
        btnShowChart = findViewById(R.id.btnShowChart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnShowChart.setOnClickListener(v -> {
            String s1 = etVal1.getText().toString();
            String s2 = etVal2.getText().toString();
            String s3 = etVal3.getText().toString();

            if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                Toast.makeText(this, "Completati toate campurile!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                float v1 = Float.parseFloat(s1);
                float v2 = Float.parseFloat(s2);
                float v3 = Float.parseFloat(s3);

                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putFloatArray("chart_values", new float[]{v1, v2, v3});
                intent.putExtras(bundle);
                startActivity(intent);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduceti doar numere!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}