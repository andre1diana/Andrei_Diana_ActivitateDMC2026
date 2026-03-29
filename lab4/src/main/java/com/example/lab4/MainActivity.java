package com.example.lab4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAddCadou;
    private ListView lvCadouri;

    private ArrayList<Cadou> listaCadouri;
    // private ArrayAdapter<Cadou> adapter;

    private CadouAdapter adapter;
    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            if (bundle != null) {
                                // Cadou cadou = (Cadou) bundle.getSerializable("cadou");
                                Cadou cadou = result.getData().getParcelableExtra("cadou");
//                                if (cadou != null) {
//                                    listaCadouri.add(cadou);
//                                    adapter.notifyDataSetChanged();
//                                }
                                if (cadou != null) {

                                    int position = result.getData().getIntExtra("position", -1);

                                    if (position != -1) {
                                        listaCadouri.set(position, cadou); // EDIT
                                    } else {
                                        listaCadouri.add(cadou); // ADD
                                    }

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCadou = findViewById(R.id.btnAddCadou);
        lvCadouri = findViewById(R.id.lvCadouri);

        listaCadouri = new ArrayList<>();

//        adapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                listaCadouri
//        );

        adapter = new CadouAdapter(this, listaCadouri);
        lvCadouri.setAdapter(adapter);

        btnAddCadou.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GetPresent.class);
            launcher.launch(intent);
        });

//        lvCadouri.setOnItemClickListener((parent, view, position, id) -> {
//            Cadou cadou = listaCadouri.get(position);
//            Toast.makeText(
//                    MainActivity.this,
//                    cadou.toString(),
//                    Toast.LENGTH_SHORT
//            ).show();
//        });

        lvCadouri.setOnItemClickListener((parent, view, position, id) -> {
            Cadou cadou = listaCadouri.get(position);

            Intent intent = new Intent(MainActivity.this, GetPresent.class);
            intent.putExtra("cadou", cadou);
            intent.putExtra("position", position);

            launcher.launch(intent);
        });

        lvCadouri.setOnItemLongClickListener((parent, view, position, id) -> {
            Cadou cadou = listaCadouri.get(position);
            listaCadouri.remove(position);
            adapter.notifyDataSetChanged();

            Toast.makeText(
                    MainActivity.this,
                    "Cadoul a fost șters: " + cadou.getMessage(),
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        });
    }
}