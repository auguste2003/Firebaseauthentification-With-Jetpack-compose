package com.example.tryhardclone


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

class PageClient : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Définit le contenu de l'activité en utilisant Jetpack Compose.
        setContent {
            // Utilisez AppNavigation comme racine de votre UI
            Title()




        }
    }
}
@Composable
fun Title(navController: NavController? = null){ // Afichhe un simple texte de bienvenue
    Text(text = "Bienvenu dans la page des clients ")
}

