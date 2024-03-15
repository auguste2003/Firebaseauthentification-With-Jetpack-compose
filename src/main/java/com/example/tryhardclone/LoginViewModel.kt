package com.example.tryhardclone


// Importations nécessaires pour utiliser ViewModel, le coroutines scope du ViewModel, Firebase Authentication, les StateFlows pour la gestion d'état réactive, et le support des coroutines pour Firebase avec .await().
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.util.Log // Importez la classe Log
// Définition de la classe LoginViewModel qui hérite de ViewModel.
// ViewModel permet de survivre aux changements de configuration (comme les rotations d'écran) et est conçu pour stocker et gérer les données liées à l'UI.
class LoginViewModel : ViewModel() {
    // _loginStatus est un MutableStateFlow privé qui permet de suivre l'état de la connexion.
    // StateFlow est une version spéciale de LiveData conçue pour les coroutines, offrant un flux d'état immuable en dehors de la classe.
    private val _loginStatus = MutableStateFlow<LoginStatus>(LoginStatus.Empty)
    // loginStatus est la version publique immuable de _loginStatus, exposée à l'extérieur de la classe pour l'observation.
    val loginStatus: StateFlow<LoginStatus> = _loginStatus

    // Fonction login prenant un email et un mot de passe pour tenter une connexion avec Firebase Authentication.
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Connexion avec Firebase Authentication
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
                // Après la connexion réussie, obtenir l'UID de l'utilisateur
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user?.uid ?: throw IllegalStateException("Utilisateur non connecté ou UID non disponible")

                // Récupérer le rôle de l'utilisateur en utilisant l'UID
                val userRole = getUserRole(uid)
                _loginStatus.value = LoginStatus.Success(userRole)
            } catch (e: Exception) {
                _loginStatus.value = LoginStatus.Failure
                Log.e("LoginViewModel", "Erreur de connexion", e)
            }
        }
    }

    // Fonction fictive pour récupérer le rôle de l'utilisateur. À implémenter.
    // Une méthode suspendue pour récupérer le rôle de l'utilisateur
    // Fonction pour récupérer le rôle de l'utilisateur en utilisant son UID
    suspend fun getUserRole(uid: String): UserRole {
        val db = FirebaseFirestore.getInstance() // Obtenez une instance de Firestore
        return try {
            // Récupérez le document pour l'utilisateur basé sur l'UID
            val docSnapshot = db.collection("users").document(uid).get().await()
            // Récupérez le champ "role" du document
            val roleString = docSnapshot.getString("role") ?: "UNKNOWN"
            // Convertissez la chaîne en UserRole
            when (roleString) {
                "client" -> UserRole.CLIENT
                "vendeur" -> UserRole.VENDEUR
                else -> UserRole.UNKNOWN // Gérez les cas inattendus
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Erreur lors de la récupération du rôle avec UID $uid", e) // Log l'erreur
            UserRole.UNKNOWN // En cas d'erreur, retournez un rôle par défaut ou lancez une erreur
        }
    }


}




/**
 * Une solution consiste à utiliser une classe scellée à la place d'une
 * énumération pour modéliser les états de connexion avec des données supplémentaires
 * pour le succès. Voici comment vous pourriez redéfinir LoginStatus en tant que classe scellée :
 */
sealed class LoginStatus {
    object Empty : LoginStatus()
    object Failure : LoginStatus()
    data class Success(val userRole: UserRole) : LoginStatus()
}

enum class UserRole {
    CLIENT, VENDEUR, UNKNOWN // Exemple de rôles
}