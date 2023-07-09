package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "561b3e84e36c4cc7b33160706230807";
    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?q=London&key=" + API_KEY;
    private TextView weatherTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherTextView = findViewById(R.id.weatherTextView);
        new FetchWeatherTask().execute();
    }
    private class FetchWeatherTask extends AsyncTask<Void, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(API_URL)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonResponse = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(jsonResponse, WeatherData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(WeatherData weatherData) {
            if (weatherData != null) {
                double temperature = weatherData.current.temp_c;
                String description = weatherData.current.condition.text;

                String weatherInfo = "Temperature: " + temperature + "°C\nDescription: " + description;
                weatherTextView.setText(weatherInfo);
            } else {
                weatherTextView.setText("Failed to fetch weather data");
            }
        }
    }

         }