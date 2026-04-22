package com.assignmentAli.com.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun createUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signOut() {
        auth.signOut()
    }

    /**
     * Maps Firebase exceptions to string keys for [R.string] lookup.
     * Many sign-up failures were shown as "invalid_credentials" because unhandled
     * [FirebaseAuthException.errorCode] values fell through to the default.
     */
    fun mapAuthError(throwable: Throwable?): String {
        if (throwable == null) return "unknown_error"

        return when (throwable) {
            is FirebaseNetworkException -> "network_error"
            is FirebaseAuthWeakPasswordException -> "weak_password"
            is FirebaseAuthUserCollisionException -> "email_in_use"
            is FirebaseAuthInvalidCredentialsException -> "invalid_credentials"
            is FirebaseAuthException -> {
                when (throwable.errorCode ?: "") {
                    "ERROR_INVALID_EMAIL" -> "invalid_email"
                    "ERROR_WRONG_PASSWORD",
                    "ERROR_USER_NOT_FOUND",
                    "ERROR_USER_DISABLED",
                    "ERROR_INVALID_CREDENTIAL" -> "invalid_credentials"
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "email_in_use"
                    "ERROR_WEAK_PASSWORD" -> "weak_password"
                    "ERROR_OPERATION_NOT_ALLOWED" -> "auth_not_configured"
                    "ERROR_TOO_MANY_REQUESTS" -> "too_many_requests"
                    else -> "invalid_credentials"
                }
            }
            else -> "invalid_credentials"
        }
    }
}
