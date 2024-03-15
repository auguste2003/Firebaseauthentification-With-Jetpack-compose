package com.example.tryhardclone


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

class PasswordResetScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                PasswordResetScreen()
            }
        }
    }
}

// Déclaration d'un composable avec un ViewModel par défaut, utilisable pour la réinitialisation de mot de passe.
@Composable
fun PasswordResetScreen(
    passwordResetViewModel: PasswordResetViewModel = viewModel(),
    navController: NavController? = null // Ajoutez NavController en paramètre
) {
    // Crée et maintient un état pour l'adresse e-mail, réinitialisé lors de recompositions sans perdre sa valeur.
    var email by remember { mutableStateOf("") }
    // Observez le statut de connexion pour réagir aux changements
    var resetStatus by remember {
        mutableStateOf(false)
    }
    // Création d'une colonne pour organiser verticalement les éléments de l'UI.
    Column(
        modifier = Modifier.padding(16.dp), // Ajout d'un padding autour de la colonne.
        horizontalAlignment = Alignment.CenterHorizontally // Centrage horizontal des éléments de la colonne.
    ) {
        // Affiche un texte indiquant l'action à l'utilisateur.
        Text(text = "Réinitialiser votre mot de passe")

        Spacer(modifier = Modifier.height(40.dp))
        // Champ de texte permettant à l'utilisateur de saisir son adresse e-mail.
        OutlinedTextField(
            value = email, // Liaison de la valeur du champ à l'état `email`.
            onValueChange = {
                email = it
            }, // Mise à jour de l'état `email` à chaque modification du champ.
            label = { Text("Email") } // Etiquette du champ indiquant son usage.
        )
        if (resetStatus) {
            Text("Lien de configuration envoyé")
        }
        // Bouton sur lequel l'utilisateur cliquera pour envoyer la demande de réinitialisation.
        Button(
            onClick = {
                resetStatus = true


                passwordResetViewModel.sendPasswordResetEmail(email)

            } // Appelle la fonction du ViewModel pour envoyer l'email.
        ) {
            // Texte affiché sur le bouton.
            Text("Envoyer le lien de réinitialisation")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PasswordResetScreenPage() {
    PasswordResetScreen()
}
