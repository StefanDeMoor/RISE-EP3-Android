package com.example.riseep3.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.riseep3.ui.screens.category.CategoryScreen
import com.example.riseep3.ui.screens.home.HomeScreen
import com.example.riseep3.ui.screens.home.HomeViewModel
import com.example.riseep3.ui.screens.overview.OverviewScreen
import com.example.riseep3.ui.screens.products.ProductScreen
import com.example.riseep3.ui.screens.profile.ProfileScreen
import com.example.riseep3.ui.screens.sales.SalesScreen
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun HomeCalcApp(
    viewModel: HomeViewModel,
    navController: NavHostController = rememberNavController()
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    RiseTheme(darkTheme = isDarkTheme) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onCreateClick = { navController.navigate("category") },
                    onProfileClick = { navController.navigate("profile") },
                    onSalesClick = { navController.navigate("sales") },
                    onProductsClick = { navController.navigate("products") },
                    viewModel = viewModel
                )
            }
            composable("category") {
                CategoryScreen(
                    onCategoryClick = { categoryName ->
                        navController.navigate("overview/${categoryName}")
                    }
                )
            }
            composable(
                "overview/{categoryName}",
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Onbekend"
                OverviewScreen(categoryName = categoryName)
            }
            composable("profile") {
                ProfileScreen(

                )
            }
            composable("sales") {
                SalesScreen(

                )
            }
            composable("products") {
                ProductScreen(

                )
            }
        }
    }
}
