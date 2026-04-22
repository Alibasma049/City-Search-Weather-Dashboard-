package com.assignmentAli.com.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignmentAli.com.data.SavedCityUi
import com.assignmentAli.com.firestore.SavedCitiesRepository
import com.google.firebase.auth.FirebaseAuth

class SavedCitiesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SavedCitiesRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _savedCities = MutableLiveData<List<SavedCityUi>>()
    val savedCities: LiveData<List<SavedCityUi>> = _savedCities

    init {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            repository.observeSavedCities(uid, _savedCities)
        } else {
            _savedCities.value = emptyList()
        }
    }

    fun deleteCity(cityId: String) {
        val uid = auth.currentUser?.uid ?: return
        repository.deleteCity(uid, cityId) { }
    }

    override fun onCleared() {
        super.onCleared()
        repository.removeSavedCitiesListener()
    }
}
