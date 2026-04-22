package com.assignmentAli.com.repository;

import androidx.lifecycle.MutableLiveData;

import com.assignmentAli.com.model.City;
import com.assignmentAli.com.model.Weather;
import com.assignmentAli.com.network.WeatherApiService;

import java.util.List;

public class WeatherRepository {

    private final WeatherApiService api = new WeatherApiService();

    public void searchCities(String query,
                             MutableLiveData<List<City>> liveData,
                             MutableLiveData<Boolean> isLoading) {
        api.searchCities(query, liveData, isLoading);
    }

    public void getWeather(String city, MutableLiveData<Weather> liveData) {
        api.getWeather(city, liveData);
    }
}
