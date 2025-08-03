package com.example.riseep3.screentests

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
import com.example.riseep3.ui.screens.products.ProductScreen
import com.example.riseep3.ui.theme.ThemeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun productScreen_displaysProductsText() {
        composeTestRule.setContent {
            ProductScreen(
                themeViewModel = ThemeViewModel(),
                onNavigateBack = {}
            )
        }

        composeTestRule
            .onNodeWithText("Products")
            .assertIsDisplayed()
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun productScreen_backButtonClick_triggersCallback() {
        composeTestRule.setContent {
            var backClicked by remember { mutableStateOf(false) }

            ProductScreen(
                themeViewModel = ThemeViewModel(),
                onNavigateBack = { backClicked = true }
            )

            if (backClicked) {
                Text("BackClicked")
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText("BackClicked")
            .assertIsDisplayed()
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @Test
    fun productScreen_toggleTheme_changesTheme() {
        val fakeThemeViewModel = FakeThemeViewModel()

        composeTestRule.setContent {
            ProductScreen(
                themeViewModel = fakeThemeViewModel,
                onNavigateBack = {}
            )

            if (fakeThemeViewModel.isDarkTheme.value) {
                Text("Dark Mode On")
            }
        }

        composeTestRule
            .onNodeWithTag("ThemeToggleButton")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Switch to light mode")
            .assertIsDisplayed()
    }

}
