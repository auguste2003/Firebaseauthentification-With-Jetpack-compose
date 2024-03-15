package com.example.tryhardclone


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

// Définition d'une classe ViewModel pour encapsuler la logique de réinitialisation de mot de passe.
class PasswordResetViewModel : ViewModel() {

    // Fonction pour envoyer l'e-mail de réinitialisation du mot de passe.
    fun sendPasswordResetEmail(email: String) {
        // Obtention de l'instance FirebaseAuth.
        val auth = Firebase.auth

        // Envoie un e-mail de réinitialisation du mot de passe à l'adresse fournie.
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                // Vérifie si l'envoi a réussi.
                if (task.isSuccessful) {
                    // Log pour le succès de l'opération.
                    Log.d(TAG, "Email sent.")
                    // Ici, vous pouvez notifier l'utilisateur du succès via l'UI.
                } else {
                    // Log en cas d'échec de l'opération, avec le message d'erreur.
                    Log.w(TAG, "Failure: ${task.exception?.message}")
                    // Ici, gérez l'échec, par exemple en notifiant l'utilisateur via l'UI.
                }
            }
    }
    public enum class resetStatus() {
        send,nosend
    }

    // Objet compagnon pour contenir des constantes statiques, ici pour le tag de log.
    companion object {
        private const val TAG = "PasswordResetVM"
    }
}

