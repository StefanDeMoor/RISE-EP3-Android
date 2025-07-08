package com.example.riseep3

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.ui.screens.home.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_clickCreateButton_triggersCallback() {
        composeTestRule.setContent {
            var createClicked by remember { mutableStateOf(false) }

            HomeScreen(
                onCreateClick = { createClicked = true },
                onProfileClick = {},
                onSalesClick = {},
                onProductsClick = {}
            )

            if (createClicked) {
                Text("Clicked")
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Create")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText("Clicked")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysText() {
        composeTestRule.setContent {
            HomeScreen(
                onCreateClick = {},
                onProfileClick = {},
                onSalesClick = {},
                onProductsClick = {}
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