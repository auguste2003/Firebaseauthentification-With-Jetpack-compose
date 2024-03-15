package com.example.tryhardclone


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

class PageVendeur : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Définit le contenu de l'activité en utilisant Jetpack Compose.
        setContent {
            // Utilisez AppNavigation comme racine de votre UI
            TitleDeLaPageVendeur()




        }
    }
}
@Composable
fun TitleDeLaPageVendeur(navController: NavController?=null){
    Text(text = "Bienvenue dans la page client ")
}