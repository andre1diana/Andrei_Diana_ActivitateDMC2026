package mta.computers.lab10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCityName;
    private Button btnSearch;
    private Button btnWeather;
    private TextView tvCityKey;
    private TextView tvWeatherInfo;
    private Spinner spDays;
    private String cityKey;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etCityName = findViewById(R.id.etCityName);
        btnSearch = findViewById(R.id.btnSearch);
        btnWeather = findViewById(R.id.btnWeather);
        tvCityKey = findViewById(R.id.tvCityKey);
        tvWeatherInfo = findViewById(R.id.tvWeatherInfo);
        spDays = findViewById(R.id.spDays);

        // Configurare Spinner
        String[] options = {"1 zi", "5 zile"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDays.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSearch.setOnClickListener(v -> {
            String city = etCityName.getText().toString().trim();
            if (!city.isEmpty()) {
                new FetchCityKeyTask(this).execute(city);
            } else {
                Toast.makeText(MainActivity.this, "Introduceti un oras", Toast.LENGTH_SHORT).show();
            }
        });

        btnWeather.setOnClickListener(v -> {
            if (cityKey != null) {
                String selection = spDays.getSelectedItem().toString();
                String days = selection.contains("1") ? "1day" : "5day";
                new FetchWeatherTask(this).execute(cityKey, days);
            } else {
                Toast.makeText(this, "Cauta un oras mai intai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class FetchCityKeyTask extends AsyncTask<String, Void, String> {
        private final WeakReference<MainActivity> activityRef;

        FetchCityKeyTask(MainActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            MainActivity activity = activityRef.get();
            if (activity == null) return null;

            String cityName = params[0];
            String url = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + activity.API_KEY + "&q=" + cityName;

            Request request = new Request.Builder().url(url).build();

            try (Response response = activity.client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();
                    if (jsonArray.size() > 0) {
                        return jsonArray.get(0).getAsJsonObject().get("Key").getAsString();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            MainActivity activity = activityRef.get();
            if (activity == null) return;

            if (result != null) {
                activity.cityKey = result;
                activity.tvCityKey.setText("Codul orasului: " + result);
                activity.tvWeatherInfo.setText("");
            } else {
                activity.cityKey = null;
                activity.tvCityKey.setText("Orasul nu a fost gasit sau eroare de retea.");
            }
        }
    }

    private static class FetchWeatherTask extends AsyncTask<String, Void, String> {
        private final WeakReference<MainActivity> activityRef;

        FetchWeatherTask(MainActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            MainActivity activity = activityRef.get();
            if (activity == null) return null;

            String key = params[0];
            String daysParam = params[1]; // "1day" sau "5day"
            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/" + daysParam + "/" + key + "?apikey=" + activity.API_KEY + "&metric=true";

            Request request = new Request.Builder().url(url).build();

            try (Response response = activity.client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                    JsonArray forecasts = jsonObject.getAsJsonArray("DailyForecasts");
                    
                    if (forecasts != null && forecasts.size() > 0) {
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < forecasts.size(); i++) {
                            JsonObject dayForecast = forecasts.get(i).getAsJsonObject();
                            String date = dayForecast.get("Date").getAsString().substring(0, 10);
                            JsonObject temp = dayForecast.getAsJsonObject("Temperature");
                            double min = temp.getAsJsonObject("Minimum").get("Value").getAsDouble();
                            double max = temp.getAsJsonObject("Maximum").get("Value").getAsDouble();
                            
                            result.append("Data: ").append(date).append("\n")
                                  .append("Min: ").append(min).append(" °C | ")
                                  .append("Max: ").append(max).append(" °C\n\n");
                        }
                        return result.toString();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            MainActivity activity = activityRef.get();
            if (activity == null) return;

            if (result != null) {
                activity.tvWeatherInfo.setText(result);
            } else {
                activity.tvWeatherInfo.setText("Eroare la preluarea vremii.");
            }
        }
    }
}