package com.example.riseep3.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.riseep3.ui.screens.category.CategoryScreen
import com.example.riseep3.ui.screens.customer.CustomerScreen
import com.example.riseep3.ui.screens.home.HomeScreen
import com.example.riseep3.ui.screens.overview.OverviewScreen
import com.example.riseep3.ui.screens.products.ProductScreen
import com.example.riseep3.ui.screens.sales.SalesScreen
import com.example.riseep3.ui.theme.RiseTheme
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun HomeCalcApp(
    navController: NavHostController = rememberNavController(),
    themeViewModel: ThemeViewModel = viewModel()
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    RiseTheme(darkTheme = isDarkTheme) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onCreateClick = { navController.navigate("category") },
                    onCustomerClick = { navController.navigate("customer") },
                    onSalesClick = { navController.navigate("sales") },
                    onProductsClick = { navController.navigate("products") },
                    themeViewModel = themeViewModel
                )
            }
            composable("category") {
                CategoryScreen(
                    onCategoryClick = { overviewId -> navController.navigate("overview/$overviewId") },
                    onNavigateBack = { navController.navigate("home") },
                    themeViewModel =  themeViewModel,
                )
            }
            composable(
                "overview/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: -1
                OverviewScreen(
                    overviewId = id,
                    themeViewModel = themeViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable("customer") {
                CustomerScreen(
                    onNavigateBack = { navController.navigate("home") },
                    themeViewModel =  themeViewModel,
                )
            }
            composable("sales") {
                SalesScreen(
                    onNavigateBack = { navController.navigate("home") },
                    themeViewModel =  themeViewModel,
                )
            }
            composable("products") {
                ProductScreen(
                    onNavigateBack = { navController.navigate("home") },
                    themeViewModel =  themeViewModel,
                )
            }
        }
    }
}