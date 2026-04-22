package com.assignmentAli.com.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignmentAli.com.model.Weather;
import com.assignmentAli.com.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<Weather> weather = new MutableLiveData<>();
    private final WeatherRepository repository = new WeatherRepository();

    public LiveData<Weather> getWeather() {
        return weather;
    }

    public void loadWeather(String city) {
        repository.getWeather(city, weather);
    }
}
