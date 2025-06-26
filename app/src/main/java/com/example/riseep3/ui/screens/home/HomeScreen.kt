package com.example.riseep3.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column( // Verticale layout voor de elementen
        modifier = modifier // Past de opgegeven modifier toe
            .fillMaxSize() // Laat de column de volledige ruimte innemen
            .padding(24.dp), // Voegt 24dp marge toe aan alle kanten
        verticalArrangement = Arrangement.Center, // Centreert items verticaal
        horizontalAlignment = Alignment.CenterHorizontally // Centreert items horizontaal
    ) {
        Text(text = "Welcome!", style = MaterialTheme.typography.headlineLarge) // Toont grote tekst 'Welcome!'
        Spacer(modifier = Modifier.height(32.dp)) // Voegt 32dp ruimte toe onder de tekst
        Row( // Horizontale layout voor de knoppen
            horizontalArrangement = Arrangement.spacedBy(24.dp), // 24dp ruimte tussen de knoppen
            verticalAlignment = Alignment.CenterVertically // Centreert de knoppen verticaal binnen de row
        ) {
            IconButton( // Eerste knop met icoon
                onClick = { /* TODO: Handle login */ }, // Actie bij klikken (nog te implementeren)
                modifier = Modifier
                    .size(96.dp) // Grootte van 96dp
                    .aspectRatio(1f) // Zorgt ervoor dat het vierkant blijft
            ) {
                Icon(Icons.Default.Create, contentDescription = "Create", modifier = Modifier.fillMaxSize(0.6f)) // Icoon voor 'Create' in de knop, iets kleiner dan knopgrootte
            }
            IconButton( // Tweede knop met icoon
                onClick = { /* TODO: Handle sign up */ }, // Actie bij klikken (nog te implementeren)
                modifier = Modifier
                    .size(96.dp) // Grootte van 96dp
                    .aspectRatio(1f) // Houdt de knop vierkant
            ) {
                Icon(Icons.Default.Face, contentDescription = "Profile", modifier = Modifier.fillMaxSize(0.6f)) // Icoon voor 'Profile' in de knop, iets kleiner dan knopgrootte
            }
        }
    }
}

@Preview(showBackground = true) // Zorgt dat de composable zichtbaar is in de preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}