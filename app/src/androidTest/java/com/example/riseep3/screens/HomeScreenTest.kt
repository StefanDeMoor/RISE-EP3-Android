package com.example.riseep3.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.ui.screens.category.CategoryScreen
import com.example.riseep3.ui.screens.customer.CustomerScreen
import com.example.riseep3.ui.screens.home.HomeScreen
import com.example.riseep3.ui.screens.products.ProductScreen
import com.example.riseep3.ui.screens.sales.SalesScreen
import com.example.riseep3.ui.theme.ThemeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun homeScreen_navigationFlow() {
        composeTestRule.setContent {
            val themeViewModel = ThemeViewModel()
            var currentScreen by remember { mutableStateOf("home") }

            when (currentScreen) {
                "home" -> HomeScreen(
                    onCreateClick = { currentScreen = "create" },
                    onCustomerClick = { currentScreen = "customer" },
                    onSalesClick = { currentScreen = "sales" },
                    onProductsClick = { currentScreen = "product" },
                    themeViewModel = themeViewModel
                )

                "create" -> CategoryScreen(
                    themeViewModel = themeViewModel,
                    onNavigateBack = { currentScreen = "home" }
                )
                "sales" -> SalesScreen(
                    themeViewModel = themeViewModel,
                    onNavigateBack = { currentScreen = "home" }
                )
//                "customer" -> CustomerScreen(
//                    themeViewModel = themeViewModel,
//                    onNavigateBack = { currentScreen = "home" }
//                )
                "product" -> ProductScreen(
                    themeViewModel = themeViewModel,
                    onNavigateBack = { currentScreen = "home" }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Create")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("CategoryScreenTitle").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Sales")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("SalesScreenTitle").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        composeTestRule.waitForIdle()

//        composeTestRule.onNodeWithContentDescription("Customer")
//            .assertIsDisplayed()
//            .performClick()
//
//        composeTestRule.waitForIdle()
//
//        composeTestRule.onNodeWithTag("CustomerScreenTitle").assertIsDisplayed()
//
//        composeTestRule.onNodeWithContentDescription("Back").performClick()
//
//        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Products")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("ProductScreenTitle").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Create").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Sales").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Customer").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Products").assertIsDisplayed()
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun homeScreen_displaysText() {
        composeTestRule.setContent {
            HomeScreen(
                onCreateClick = {},
                onCustomerClick = {},
                onSalesClick = {},
                onProductsClick = {},
                themeViewModel =  ThemeViewModel(),
            )
        }

        composeTestRule
            .onNodeWithText("Welcome to")
            .assertExists()

        composeTestRule
            .onNodeWithText("HOMECALC")
            .assertExists()
    }
}