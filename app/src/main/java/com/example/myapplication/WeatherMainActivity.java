package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherMainActivity extends AppCompatActivity {
private  static final String API_KEY= "";
private static final String API_URL="v1/current.json?q=London&key="+API_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);
        new FetchWeatherTask().execute();
    }
    private class FetchWeatherTask extends AsyncTask<Void,Void,Weather_Data>
    {

        @Override
        protected Weather_Data doInBackground(Void... voids) {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder().url(API_URL).build();
          try {
              Response response=okHttpClient.newCall(request).execute();
              String jsonResponse=response.body().string();
              Gson gson= new Gson();
              return  gson.fromJson(jsonResponse,Weather_Data.class);


          } catch (IOException e) {
              e.printStackTrace();
          }
return null;
        }
        @Override
        protected void onPostExecute(Weather_Data weather_data)
        {
            if (weather_data!=null)
            {
                double temparature=weather_data.current_data.temp_c;
                String description=weather_data.current_data.condition_data.text1;
                String weatherInfo= "Temparature: "+temparature+"\nDescription: "+description;
            }
            else {
                String weatherInfo="Failed to fetch WeatherInfo";
            }
        }
    }

}