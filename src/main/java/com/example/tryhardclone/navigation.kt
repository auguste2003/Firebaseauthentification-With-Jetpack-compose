package com.example.tryhardclone


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
@Composable

fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            // Assurez-vous que LoginPage prend NavController comme paramètre
            LoginPage(navController = navController)
        }
        composable("register") {
            // La même logique appliquée pour RegisterPage
            RegisterPage(navController = navController)
        }
        composable("passwordReset") {
            // Assurez-vous que PasswordResetScreen prend NavController comme paramètre
            PasswordResetScreen(navController = navController)
        }
        composable("pageClient"){
            Title(navController = navController)
        }
        composable("pageVendeur"){
            TitleDeLaPageVendeur(navController = navController)
        }

        // Ajoutez d'autres destinations selon vos besoins
    }
}

