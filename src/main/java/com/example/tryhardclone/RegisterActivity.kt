package com.example.tryhardclone


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                RegisterPage()
            }
        }
    }
}



@Composable
fun RegisterPage(registerViewModel: RegisterViewModel = viewModel(),
                 navController: NavController? =null, // Ajoutez NavController en paramètre
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    // var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    // Observer registrationStatus pour réagir aux changements
    val registrationStatus by registerViewModel.registrationStatus.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 60.dp), // Ajoutez du padding en haut pour l'espace du logo
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(40.dp))

        Text("Inscription", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'utilisateur") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        /*   OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Numéro de téléphone") },
            modifier = Modifier.fillMaxWidth()
        ) */
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {   // Afficher les informations saisies dans la console.
                // Appel de la fonction registerUser du ViewModel avec l'email et le mot de passe
                registerViewModel.registerUser(email, password)
                println("Informations de l'utilisateur Apres inscription:")
                println("Nom d'utilisateur: $username")
                println("Email: $email")
                println("Mot de passe: $password")


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("S'inscrire")
        }

    /*       val context = LocalContext.current
    // Proble á ce niveau
            val googleSignInClient = remember {
                GoogleSignIn.getClient(
                    context,
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                )
            }
            val googleSignInLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = { result ->
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        registerViewModel.signInWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }
            )



     */

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(50.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    onClick = {
                  //            googleSignInLauncher.launch(googleSignInClient.signInIntent)
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(27.dp),
                            painter = painterResource(id = R.drawable.ic_launcher_background) ,
                            contentDescription =null )
                        Text(
                            modifier = Modifier
                            ,

                            text = "Se Connecter avec Google",
                            fontFamily = FontFamily.SansSerif
                        )
                    }


                }

                Button( modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =Color.Yellow,
                        contentColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    onClick = {
                       // googleSignInLauncher.launch(googleSignInClient.signInIntent)
                    }){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(27.dp)
                            ,
                            painter = painterResource(R.drawable.ic_launcher_background) ,
                            contentDescription = null)
                        Text(
                            // modifier = Modifier.fillMaxWidth(),
                            text = "Se Connecter avec Facebook",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            //  fontFamily = poppins
                        )
                    }

                }
            }






    }
    /* when (registrationStatus) {
         RegistrationStatus.Success -> {
            // Text(text = " Inscription réussie: ")
             LaunchedEffect(Unit) {

                 navController?.navigate("login") {
                     // Configurer la navigation pour éviter de revenir à l'écran d'inscription après la connexion
                     popUpTo("login") { inclusive = true }
                 }
             }
         }
         RegistrationStatus.Failure -> {
             val errorMessage = "Erreur inconnue" // Utilisez le message d'erreur de votre ViewModel
             Text(text = "Erreur: $errorMessage")
         }
         else -> Unit
     }

     */
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterPage(){
    RegisterPage()
}
