package com.assignmentAli.com.auth

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun signOut() {
        repository.signOut()
    }
}
