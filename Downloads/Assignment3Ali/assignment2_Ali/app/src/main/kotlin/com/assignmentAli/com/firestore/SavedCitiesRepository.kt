package com.assignmentAli.com.firestore

import androidx.lifecycle.MutableLiveData
import com.assignmentAli.com.data.SavedCityUi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions

class SavedCitiesRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private var savedCitiesListener: ListenerRegistration? = null

    fun saveOrUpdateCity(
        uid: String,
        cityId: String,
        name: String,
        region: String,
        country: String,
        onComplete: (Exception?) -> Unit
    ) {
        val doc = db.collection("users").document(uid)
            .collection("savedCities")
            .document(cityId)
        val data = hashMapOf(
            "name" to name,
            "region" to region,
            "country" to country,
            "lastSearched" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )
        doc.set(data, SetOptions.merge()).addOnCompleteListener { task ->
            onComplete(task.exception)
        }
    }

    fun deleteCity(uid: String, cityId: String, onComplete: (Exception?) -> Unit) {
        db.collection("users").document(uid)
            .collection("savedCities")
            .document(cityId)
            .delete()
            .addOnCompleteListener { task -> onComplete(task.exception) }
    }

    fun isCitySaved(uid: String, cityId: String, callback: (Boolean) -> Unit) {
        if (cityId.isEmpty()) {
            callback(false)
            return
        }
        db.collection("users").document(uid)
            .collection("savedCities")
            .document(cityId)
            .get()
            .addOnSuccessListener { snap -> callback(snap.exists()) }
            .addOnFailureListener { callback(false) }
    }

    fun observeSavedCities(uid: String, liveData: MutableLiveData<List<SavedCityUi>>) {
        savedCitiesListener?.remove()
        savedCitiesListener = db.collection("users").document(uid)
            .collection("savedCities")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    liveData.postValue(emptyList())
                    return@addSnapshotListener
                }
                val list = snapshot?.documents?.mapNotNull { doc ->
                    val name = doc.getString("name") ?: return@mapNotNull null
                    val region = doc.getString("region") ?: ""
                    val country = doc.getString("country") ?: ""
                    val ts = doc.getTimestamp("lastSearched")
                    SavedCityUi(doc.id, name, region, country, ts)
                }?.sortedByDescending { it.lastSearched?.toDate()?.time ?: 0L } ?: emptyList()
                liveData.postValue(list)
            }
    }

    fun removeSavedCitiesListener() {
        savedCitiesListener?.remove()
        savedCitiesListener = null
    }
}
