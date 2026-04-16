package mta.computers.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etSearchMessage, etMinWeight, etMaxWeight, etDeleteThreshold, etStartingLetter;
    private ListView lvGifts;
    private List<Gift> giftList;
    private ArrayAdapter<String> adapter;
    private List<String> displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // UI Initialization
        lvGifts = findViewById(R.id.lvGifts);
        etSearchMessage = findViewById(R.id.etSearchMessage);
        etMinWeight = findViewById(R.id.etMinWeight);
        etMaxWeight = findViewById(R.id.etMaxWeight);
        etDeleteThreshold = findViewById(R.id.etDeleteThreshold);
        etStartingLetter = findViewById(R.id.etStartingLetter);

        displayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvGifts.setAdapter(adapter);

        findViewById(R.id.btnGoToAdd).setOnClickListener(v -> {
            startActivity(new Intent(this, AddGiftActivity.class));
        });

        findViewById(R.id.btnSelectAll).setOnClickListener(v -> {
            refreshList(dbHelper.getAllGifts());
        });

        findViewById(R.id.btnSearchByMessage).setOnClickListener(v -> {
            Gift gift = dbHelper.getGiftByMessage(etSearchMessage.getText().toString());
            List<Gift> result = new ArrayList<>();
            if (gift != null) result.add(gift);
            refreshList(result);
        });

        findViewById(R.id.btnSearchByRange).setOnClickListener(v -> {
            try {
                int min = Integer.parseInt(etMinWeight.getText().toString());
                int max = Integer.parseInt(etMaxWeight.getText().toString());
                refreshList(dbHelper.getGiftsInWeightRange(min, max));
            } catch (Exception e) {
                Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnDeleteUnder).setOnClickListener(v -> {
            try {
                int threshold = Integer.parseInt(etDeleteThreshold.getText().toString());
                dbHelper.deleteGiftsWithWeightLessThan(threshold);
                refreshList(dbHelper.getAllGifts());
            } catch (Exception e) {
                Toast.makeText(this, "Invalid threshold", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnIncrementWeight).setOnClickListener(v -> {
            String letter = etStartingLetter.getText().toString();
            if (!letter.isEmpty()) {
                dbHelper.incrementWeightForMessagesStartingWith(letter);
                refreshList(dbHelper.getAllGifts());
            }
        });

        refreshList(dbHelper.getAllGifts());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList(dbHelper.getAllGifts());
    }

    private void refreshList(List<Gift> gifts) {
        displayList.clear();
        for (Gift g : gifts) {
            displayList.add(g.getMessage() + " (" + g.getWeight() + "g) - " + g.getObjectType());
        }
        adapter.notifyDataSetChanged();
    }
}