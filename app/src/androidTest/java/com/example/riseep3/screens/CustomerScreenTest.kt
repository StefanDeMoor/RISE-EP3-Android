package com.example.riseep3

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.ui.screens.customer.CustomerScreen
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
import com.example.riseep3.ui.theme.ThemeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomerScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun customerScreen_displaysCustomerText() {
        composeTestRule.setContent {
            CustomerScreen(
                themeViewModel = ThemeViewModel(),
                onNavigateBack = {}
            )
        }

        composeTestRule
            .onNodeWithText("Customer")
            .assertIsDisplayed()
    }

    @SuppressLint("ViewModelConstructorInComposable")
    @Test
    fun customerScreen_backButtonClick_triggersCallback() {
        composeTestRule.setContent {
            var backClicked by remember { mutableStateOf(false) }

            CustomerScreen(
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
    fun customerScreen_toggleTheme_changesTheme() {
        val fakeThemeViewModel = FakeThemeViewModel()

        composeTestRule.setContent {
            CustomerScreen(
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
