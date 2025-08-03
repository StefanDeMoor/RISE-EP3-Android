package com.example.riseep3.navigation

import android.annotation.SuppressLint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.assertCurrentRouteName
import com.example.riseep3.ui.HomeCalcApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @SuppressLint("ViewModelConstructorInComposable")
    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            HomeCalcApp(
                navController = navController
            )
        }
    }

    @Test
    fun homeScreen_isStartDestination() {
        navController.assertCurrentRouteName("home")
    }

    @Test
    fun clickCreateButton_navigatesToCategory() {
        composeTestRule.onNodeWithContentDescription("Create").performClick()
        composeTestRule.waitForIdle()
        navController.assertCurrentRouteName("category")
    }

    @Test
    fun clickProfileButton_navigatesToProfile() {
        composeTestRule.onNodeWithContentDescription("Profile").performClick()
        composeTestRule.waitForIdle()
        navController.assertCurrentRouteName("profile")
    }

    @Test
    fun clickSalesButton_navigatesToSales() {
        composeTestRule.onNodeWithContentDescription("Sales").performClick()
        composeTestRule.waitForIdle()
        navController.assertCurrentRouteName("sales")
    }

    @Test
    fun clickProductsButton_navigatesToProducts() {
        composeTestRule.onNodeWithContentDescription("Products").performClick()
        composeTestRule.waitForIdle()
        navController.assertCurrentRouteName("products")
    }
}