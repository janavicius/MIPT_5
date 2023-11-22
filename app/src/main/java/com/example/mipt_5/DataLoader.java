package com.example.mipt_5;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataLoader extends AsyncTask<Void, Void, String> {
    private ArrayAdapter<String> adapter;

    public DataLoader(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";

        try {
            URL url = new URL("https://api.weatherapi.com/v1/current.json?key=dfb4d0cd23d94f6d9d2175040232211&q=London&aqi=no");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                reader.close();
                result = stringBuilder.toString();
            } else {
                result = "Error " + responseCode;
            }

            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error";
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject location = jsonObject.getJSONObject("location");
            String cityName = location.getString("name");

            JSONObject current = jsonObject.getJSONObject("current");
            double temperature = current.getDouble("temp_c");
            double humidity = current.getDouble("humidity");

            String weatherInfo = "City: " + cityName + ", Temperature: " + temperature + "°C, Humidity: " + humidity + "%";

            List<String> weatherData = new ArrayList<>();
            weatherData.add(weatherInfo);

            adapter.clear();
            adapter.addAll(weatherData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
