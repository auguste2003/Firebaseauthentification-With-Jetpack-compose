package com.example.tryhardclone.ui.theme

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp

class CheckoutPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TryhardCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusSeatSelection("A")
                }
            }
        }
    }
}

@Composable
fun BusSeatSelection(busType: String) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Dynamiquement ajustez le nombre de colonnes basé sur l'orientation et la taille de l'écran
    val columns = if (isLandscape && screenWidth > 600.dp) listOf(5, 4) else if (isLandscape) listOf(4, 3) else listOf(3, 2)

    val totalSeats = if (busType == "A") 70 else 30
    val numberOfSeatsPerRow = columns.sum()
    val seatStatusMap = remember { mutableStateMapOf<Int, SeatStatus>() }

    for (i in 1..totalSeats) {
        seatStatusMap[i] = SeatStatus.Available
    }

    val seatRows = List(totalSeats / numberOfSeatsPerRow) { rowIndex ->
        val startIndex = rowIndex * numberOfSeatsPerRow + 1
        val endIndex = (rowIndex + 1) * numberOfSeatsPerRow
        startIndex..endIndex
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp) // Pour l'espace du bouton
            // Centre les enfants horizontalement.
        ) {
            item {
                // Informations du trajet et du bus
                TripInfoHeader()
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Assurez-vous que le Row prend toute la largeur.
                        .padding(top = 46.dp, bottom = 40.dp), // Ajoute un padding vertical.
                    horizontalArrangement = Arrangement.Center // Centre les enfants horizontalement dans le Row.
                ) {
                    SeatStatusIndicators()
                }

            }

            items(seatRows) { rowRange ->
                SeatRow(rowRange, columns, seatStatusMap, screenWidth)
            }
        }
        // Maintenant, appelez BuyButton avec le modifier pour l'aligner en bas
        BuyButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp) // Ajustez le padding selon vos besoins
        )


    }

}

@Composable

fun TripInfoHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .background(Color(0xFF1B5E20)) // Votre couleur de fond
    ) {


        Column {
            Text(
                "Rangpur ↔ Dhaka",
                modifier = Modifier
                    .fillMaxWidth() // Prend la largeur maximale du parent
                    .wrapContentSize(Alignment.Center) // Centre le contenu à l'intérieur du Modifier
                    .padding(top = 16.dp), // Ajoutez un padding en haut si nécessaire
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 30.dp)
                    .offset(y = 30.dp) // Ajuster en fonction de la position souhaitée
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ena Paribahan", fontWeight = FontWeight.Bold)
                        Text("7:30 PM") // Ici, on place le temps à droite du nom
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Type: AC(2)")
                        Text("Price: $20") // Le prix est en haut à droite
                    }
                }
            }
        }
        // Placez les indicateurs de statut des sièges à l'extérieur et en bas de la carte

    }
}



@Composable
fun SeatStatusIndicators(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        StatusIndicator("Available",  Color.Gray)
        StatusIndicator("Selected",  Color(0xFFFF5722))
        StatusIndicator("Booked", Color(0xFF1B5E20))
    }
}
@Composable
fun StatusIndicator(text: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(Modifier.width(15.dp))
        Text(text)
    }
}

@Composable
fun SeatRow(rowRange: IntRange, columns: List<Int>, seatStatusMap: Map<Int, SeatStatus>, screenWidth: Dp) {
    // Obtenez la configuration actuelle pour déterminer la largeur de l'écran
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }
    val spaceBetweenSeatsPx = with(LocalDensity.current) { 16.dp.toPx() }
    val numberOfSpaces = columns.sum() - 1 + 2 // Espaces entre les sièges + 2 pour les marges latérales
    val seatWidthPx = (screenWidthPx - (numberOfSpaces * spaceBetweenSeatsPx)) / columns.sum()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally) // Centre le contenu de Row horizontalement
    ) {
        // Créez les sièges pour la partie gauche avec un espace dynamique
        for (seatNum in rowRange.take(columns[0])) {
            Seat(seatNum, seatStatusMap[seatNum], Dp(seatWidthPx / LocalDensity.current.density))
            Spacer(Modifier.width(16.dp)) // Espace fixe entre chaque siège
        }
        Spacer(Modifier.width(16.dp)) // Espace fixe entre les colonnes
        // Créez les sièges pour la partie droite avec un espace dynamique
        for (seatNum in rowRange.takeLast(columns[1])) {
            Seat(seatNum, seatStatusMap[seatNum], Dp(seatWidthPx / LocalDensity.current.density))
            if (seatNum != rowRange.last) {
                Spacer(Modifier.width(16.dp)) // Espace fixe entre chaque siège
            }
        }
    }
    Spacer(Modifier.height(16.dp)) // Espace fixe entre les rangées
}

fun IntRange.takeLast(count: Int): List<Int> {
    if (count <= 0) return emptyList()
    val size = this.last - this.first + 1
    if (count >= size) return this.toList()
    return this.toList().takeLast(count)
}

@Composable
fun Seat(seatNum: Int, status: SeatStatus?, size: Dp = 48.dp) {
    val color = when(status) {
        SeatStatus.Available -> Color.Gray
        SeatStatus.Selected -> Color(0xFFFF5722)
        SeatStatus.Booked -> Color(0xFF1B5E20)
        null -> Color.Gray // Fallback color
    }
    // Utilisation d'un Box Composable pour simuler un siège avec une taille dynamique
    Box(
        modifier = Modifier
            .size(size)
            .background(color, shape = RoundedCornerShape(4.dp))
            .clickable { /* Mettre à jour l'état du siège ici */ },
        contentAlignment = Alignment.Center
    ) {
        Text(text = seatNum.toString())
    }
}

@Composable
fun BuyButton(modifier: Modifier = Modifier) { // Modifier ajouté avec une valeur par défaut
    // Utilisez le modifier passé en paramètre ici, au lieu de créer un nouveau.
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 30.dp)
    ) {
        Button(
            onClick = { /* Gérer le clic ici */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier.fillMaxWidth() // Le modifier du bouton pour qu'il prenne toute la largeur
        ) {
            Text("Buy Now")
        }
    }
}



enum class SeatStatus {
    Available,
    Selected,
    Booked
}

@Preview(showBackground = true)
@Composable
fun GreatingPreview(){
    TryhardCloneTheme {
        BusSeatSelection("A")
        //  TripInfoHeader()
    }
}



