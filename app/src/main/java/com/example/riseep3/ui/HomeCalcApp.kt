package com.example.riseep3.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.riseep3.ui.screens.category.CategoryScreen
import com.example.riseep3.ui.screens.home.HomeScreen

@Composable
fun HomeCalcApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onCreateClick = { navController.navigate("category") }
            )
        }
        composable("category") {
            CategoryScreen()
        }
    }
}