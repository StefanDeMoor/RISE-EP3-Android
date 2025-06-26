package com.example.riseep3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.riseep3.ui.screens.category.CategoryScreen
import com.example.riseep3.ui.screens.category.CategoryViewModel
import com.example.riseep3.ui.screens.home.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.theme.RISEEP3Theme

class MainActivity : ComponentActivity() { // De hoofdactiviteit van de app
    override fun onCreate(savedInstanceState: Bundle?) { // Wordt aangeroepen wanneer de activiteit wordt gestart
        super.onCreate(savedInstanceState) // Roept de onCreate van de superclass aan
        enableEdgeToEdge() // Zorgt ervoor dat de UI de volledige schermruimte kan gebruiken, inclusief onder status/navigation bar
        setContent { // Zet de inhoud van het scherm met Jetpack Compose
            RISEEP3Theme { // Past de app-thema toe (kleuren, typografie, etc.)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // Layoutstructuur met standaard Material elementen
                    val categoryViewModel: CategoryViewModel = viewModel()
                    CategoryScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = categoryViewModel
                    ) // Roept je HomeScreen composable aan, met de juiste padding
                }
            }
        }
    }
}


