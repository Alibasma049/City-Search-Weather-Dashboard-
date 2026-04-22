package com.assignmentAli.com.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _errorKey = MutableLiveData<String?>()
    val errorKey: LiveData<String?> = _errorKey

    fun clearError() {
        _errorKey.value = null
    }

    fun signIn(email: String, password: String) {
        val e = email.trim()
        if (e.isEmpty() || password.isEmpty()) {
            _errorKey.value = "empty_fields"
            return
        }
        repository.signIn(e, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                _errorKey.value = repository.mapAuthError(task.exception)
            }
        }
    }

    fun signUp(email: String, password: String) {
        val e = email.trim()
        if (e.isEmpty() || password.isEmpty()) {
            _errorKey.value = "empty_fields"
            return
        }
        repository.createUser(e, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                _errorKey.value = mapSignUpDisplayKey(repository.mapAuthError(task.exception))
            }
        }
    }

    /**
     * Firebase often reports sign-up problems as [invalid_credentials]; that string is meant for
     * "wrong password" on sign-in. Remap to a registration-specific message.
     */
    private fun mapSignUpDisplayKey(technicalKey: String): String {
        return when (technicalKey) {
            "invalid_credentials",
            "unknown_error" -> "signup_failed"
            else -> technicalKey
        }
    }
}
