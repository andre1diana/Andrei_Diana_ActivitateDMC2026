package com.example.lab4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAddCadou;
    private Button btnSettings;
    private ListView lvCadouri;
    private ArrayList<Cadou> listaCadouri;
    private CadouAdapter adapter;

    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Cadou cadou = result.getData().getParcelableExtra("cadou");
                            if (cadou != null) {
                                int position = result.getData().getIntExtra("position", -1);
                                if (position != -1) {
                                    listaCadouri.set(position, cadou);
                                } else {
                                    listaCadouri.add(cadou);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCadou = findViewById(R.id.btnAddCadou);
        btnSettings = findViewById(R.id.btnSettings);
        lvCadouri = findViewById(R.id.lvCadouri);

        listaCadouri = new ArrayList<>();
        loadCadouri(); // Încarcă cadourile salvate în fișier

        adapter = new CadouAdapter(this, listaCadouri);
        lvCadouri.setAdapter(adapter);

        btnAddCadou.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GetPresent.class);
            launcher.launch(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        lvCadouri.setOnItemClickListener((parent, view, position, id) -> {
            Cadou cadou = listaCadouri.get(position);
            Intent intent = new Intent(MainActivity.this, GetPresent.class);
            intent.putExtra("cadou", (Parcelable) cadou);
            intent.putExtra("position", position);
            launcher.launch(intent);
        });

        lvCadouri.setOnItemLongClickListener((parent, view, position, id) -> {
            Cadou cadou = listaCadouri.get(position);
            saveFavorite(cadou);
            Toast.makeText(MainActivity.this, "Adăugat la favorite: " + cadou.getMessage(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void loadCadouri() {
        try (FileInputStream fis = openFileInput("cadouri.txt")) {
            while (fis.available() > 0) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                Cadou cadou = (Cadou) ois.readObject();
                if (cadou != null) {
                    listaCadouri.add(cadou);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFavorite(Cadou cadou) {
        try {
            FileOutputStream fos = openFileOutput("favorite.txt", MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cadou);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Eroare la salvarea favoritului", Toast.LENGTH_SHORT).show();
        }
    }
}