package com.assignmentAli.com.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignmentAli.com.firestore.SavedCitiesRepository
import com.google.firebase.auth.FirebaseAuth

class WeatherDashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SavedCitiesRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _bookmarkState = MutableLiveData<Boolean>()
    val bookmarkState: LiveData<Boolean> = _bookmarkState

    fun refreshBookmarkState(cityId: String) {
        val uid = auth.currentUser?.uid
        if (uid == null || cityId.isEmpty()) {
            _bookmarkState.postValue(false)
            return
        }
        repository.isCitySaved(uid, cityId) { exists ->
            _bookmarkState.postValue(exists)
        }
    }

    fun saveOrUpdateBookmark(
        cityId: String,
        name: String,
        region: String,
        country: String
    ) {
        val uid = auth.currentUser?.uid ?: return
        if (cityId.isEmpty()) return
        repository.saveOrUpdateCity(uid, cityId, name, region, country) { error ->
            if (error == null) {
                _bookmarkState.postValue(true)
            }
        }
    }
}
