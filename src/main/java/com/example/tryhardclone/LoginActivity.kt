package com.example.tryhardclone


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect

// Définition de la classe LoginActivity qui étend ComponentActivity, utilisée comme écran de connexion.
class LoginActivity : ComponentActivity() {  // Ceci hérite d'une classe ComponentActivity qui permet l'utilisation des composants
    override fun onCreate(savedInstanceState: Bundle?) { // recupere l'encien état de l'activté lorsque l'application est fermée
        super.onCreate(savedInstanceState)
        // Définit le contenu de l'activité en utilisant Jetpack Compose.
        setContent {
            // Utilisez AppNavigation comme racine de votre UI
            AppNavigation()




        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController? =null, // Ajoutez NavController en paramètre et le rend optionel
) {
    // États pour stocker l'email et le mot de passe saisis par l'utilisateur
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observez le statut de connexion pour réagir aux changements
    val loginStatus by loginViewModel.loginStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Affichage du logo.
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(40.dp)) // Espace entre le logo et le champ suivant.


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loginViewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIGN IN")
        }

        // Ici, vous pouvez ajouter la logique pour afficher les messages d'erreur ou de succès basée sur loginStatus
        when (val status = loginStatus) {
            is LoginStatus.Success -> {
                // Récupération du rôle de l'utilisateur à partir de l'état de succès
                LaunchedEffect(Unit) {// l'utilisation de LaunchedEffect(Unit) sert à
                    // s'assurer que l'action de navigation n'est exécutée qu'une
                    // seule fois, même si le composable est recomposé plusieurs fois à cause de mises à jour de l'état
                    when (status.userRole) {
                        UserRole.CLIENT -> if (navController != null) {
                            navController.navigate("pageClient")
                        }
                        UserRole.VENDEUR -> if (navController != null) {
                            navController.navigate("pageVendeur")
                        }
                        else -> {} // Gérez les autres cas si nécessaire
                    }
                }
            }
            LoginStatus.Failure -> {
                val errorMessage = "Problème au niveau du serveur"
                Text("Erreur: $errorMessage", color = Color.Red)
            }
            LoginStatus.Empty -> Unit // Ne rien faire pour l'état vide
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Pas encore enregistré ? Enregistrez-vous ici.",
            modifier = Modifier.clickable {
                if (navController != null) {
                    navController.navigate("register")
                }
            },
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Mot de passe oublié ?",
            modifier = Modifier.clickable {
                if (navController != null) {
                    navController.navigate("passwordReset")
                }
            },
            color = MaterialTheme.colorScheme.primary
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    LoginPage()

}