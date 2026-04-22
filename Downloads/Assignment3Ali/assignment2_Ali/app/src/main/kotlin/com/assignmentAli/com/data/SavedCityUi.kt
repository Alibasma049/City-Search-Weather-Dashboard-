package com.assignmentAli.com.data

import com.google.firebase.Timestamp

data class SavedCityUi(
    val cityId: String,
    val name: String,
    val region: String,
    val country: String,
    val lastSearched: Timestamp?
)
