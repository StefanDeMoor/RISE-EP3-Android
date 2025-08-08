package com.example.riseep3.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
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
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
import com.example.riseep3.ui.screens.sales.SalesScreen
import com.example.riseep3.ui.theme.ThemeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SalesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun salesScreen_displaysSalesText() {
        composeTestRule.setContent {
            SalesScreen(
                themeViewModel = ThemeViewModel(),
                onNavigateBack = {}
            )
        }

        composeTestRule
            .onNodeWithText("Sales")
            .assertIsDisplayed()
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun salesScreen_backButtonClick_triggersCallback() {
        composeTestRule.setContent {
            var backClicked by remember { mutableStateOf(false) }

            SalesScreen(
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
    fun salesScreen_toggleTheme_changesTheme() {
        val fakeThemeViewModel = FakeThemeViewModel()

        composeTestRule.setContent {
            SalesScreen(
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