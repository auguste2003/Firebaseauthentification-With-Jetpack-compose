package com.example.tryhardclone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.GoogleAuthProvider


class RegisterViewModel : ViewModel() {

    val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    private val _registrationStatus =
        MutableStateFlow<RegistrationStatus>(RegistrationStatus.Empty)
    val registrationStatus: StateFlow<RegistrationStatus> = _registrationStatus
    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Inscrire l'utilisateur avec Firebase Auth
                val authResult =
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .await()
                val userId = authResult.user?.uid
                    ?: throw IllegalStateException("UID is null after registration")

                // Stocker les données utilisateur dans Firestore
                val userData = hashMapOf(
                    "email" to email,
                    "role" to "client"
                )
                FirebaseFirestore.getInstance().collection("users").document(userId)
                    .set(userData).await()

                // Envoyer l'e-mail de vérification
                authResult.user?.sendEmailVerification()?.await()

                _registrationStatus.value = RegistrationStatus.Success
            } catch (e: Exception) {
                _registrationStatus.value = RegistrationStatus.Failure
                _errorMessage.postValue(e.message) // Utilisez le _errorMessage du membre de classe
                Log.e("RegisterUser", "Failed to register user", e) // Log pour le debug
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential).await()
                _registrationStatus.value = RegistrationStatus.Success
            } catch (e: Exception) {
                _registrationStatus.value = RegistrationStatus.Failure
                _errorMessage.postValue(e.message)
            }
        }
    }

}

enum class RegistrationStatus {
    Empty,
    Success,
    Failure // Assurez-vous que cela accepte un String? (nullable)
}