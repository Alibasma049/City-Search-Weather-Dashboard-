package com.assignmentAli.com.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignmentAli.com.model.City;
import com.assignmentAli.com.repository.WeatherRepository;

import java.util.List;

public class CitySearchViewModel extends ViewModel {

    private final MutableLiveData<List<City>> cities = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final WeatherRepository repository = new WeatherRepository();

    public LiveData<List<City>> getCities() {
        return cities;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

    public void searchCities(String query) {
        isLoading.setValue(true);
        repository.searchCities(query, cities, isLoading);
    }
}
