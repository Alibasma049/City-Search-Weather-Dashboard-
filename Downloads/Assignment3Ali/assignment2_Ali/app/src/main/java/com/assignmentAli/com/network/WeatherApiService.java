package com.assignmentAli.com.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.assignmentAli.com.model.City;
import com.assignmentAli.com.model.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherApiService {

    private static final String API_KEY = "b1104f0109ac4f19b2721854261603";
    private static final String TAG = "WeatherAPI";

    private final OkHttpClient client = new OkHttpClient();

    public void searchCities(String query,
                             MutableLiveData<List<City>> liveData,
                             MutableLiveData<Boolean> isLoading) {

        String url = "https://api.weatherapi.com/v1/search.json?key=" + API_KEY + "&q=" + query;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Search Request Failed", e);
                isLoading.postValue(false);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (!response.isSuccessful() || response.body() == null) {
                    isLoading.postValue(false);
                    return;
                }

                try {
                    JSONArray array = new JSONArray(response.body().string());
                    List<City> cities = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        long apiId = obj.optLong("id", 0L);
                        String id = apiId != 0L ? String.valueOf(apiId) : "";
                        City city = new City(
                                id,
                                obj.getString("name"),
                                obj.getString("region"),
                                obj.getString("country")
                        );
                        cities.add(city);
                    }

                    liveData.postValue(cities);

                } catch (Exception e) {
                    Log.e(TAG, "Search Parsing Error", e);
                }

                isLoading.postValue(false);
            }
        });
    }

    public void getWeather(String city, MutableLiveData<Weather> liveData) {

        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + city;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Weather Request Failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    JSONObject location = obj.getJSONObject("location");
                    JSONObject current = obj.getJSONObject("current");
                    JSONObject condition = current.getJSONObject("condition");

                    String icon = "https:" + condition.getString("icon");
                    String conditionText = condition.optString("text", "");

                    Weather weather = new Weather(
                            location.getString("name"),
                            location.optString("country", ""),
                            current.getDouble("temp_c"),
                            current.getDouble("temp_f"),
                            current.optDouble("feelslike_c", 0.0),
                            current.getInt("humidity"),
                            current.getDouble("wind_kph"),
                            current.getString("wind_dir"),
                            icon,
                            conditionText
                    );

                    liveData.postValue(weather);

                } catch (Exception e) {
                    Log.e(TAG, "Weather Parsing Error", e);
                }
            }
        });
    }
}
