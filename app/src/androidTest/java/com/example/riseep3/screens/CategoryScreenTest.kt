package com.example.riseep3.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.ui.componenten.SuccessDialog
import com.example.riseep3.ui.componenten.category.AnimatedDialogButton
import com.example.riseep3.ui.componenten.category.CategoryDropdownMenu
import com.example.riseep3.ui.componenten.category.NewItemDialog
import com.example.riseep3.ui.componenten.overview.OverviewCard
import com.example.riseep3.ui.componenten.overview.OverviewSection
import com.example.riseep3.ui.theme.RiseTheme
import org.junit.Rule
import org.junit.Test

class CategoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun categoryDropdownMenu_displaysItemsAndSelectsCategory() {
        val expanded = mutableStateOf(true)
        val selected = mutableStateOf<CategoryEntity?>(null)
        val mockCategories = listOf(
            CategoryEntity(1,"Overview"),
            CategoryEntity(2,"Fitness"),
            CategoryEntity(3,"Work")
        )

        composeTestRule.setContent {
            RiseTheme {
                CategoryDropdownMenu(
                    expanded = expanded.value,
                    selectedCategory = selected.value?.name,
                    categories = mockCategories,
                    onCategorySelected = { selected.value = it },
                    onExpandedChange = { expanded.value = it }
                )
            }
        }

        composeTestRule.onNodeWithText("Overview").assertIsDisplayed().performClick()
        assert(selected.value?.name == "Overview")
    }

    @Test
    fun animatedDialogButton_triggersClickAction() {
        var clicked = false

        composeTestRule.setContent {
            RiseTheme {
                AnimatedDialogButton(text = "Create") {
                    clicked = true
                }
            }
        }

        composeTestRule.onNodeWithText("Create").performClick()
        composeTestRule.waitForIdle()
        assert(clicked)
    }

    @Test
    fun newItemDialog_confirmClickTriggersCallback() {
        val name = mutableStateOf("Initial")
        var confirmed = false

        composeTestRule.setContent {
            RiseTheme {
                NewItemDialog(
                    itemName = name.value,
                    onNameChange = { name.value = it },
                    onConfirm = { confirmed = true },
                    onDismiss = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Title").performTextInput("Test Category")
        composeTestRule.onNodeWithText("Create").performClick()
        composeTestRule.waitForIdle()
        assert(confirmed)
    }

    @Test
    fun newItemDialog_cancelClickTriggersDismiss() {
        val name = mutableStateOf("Initial")
        var dismissed = false

        composeTestRule.setContent {
            RiseTheme {
                NewItemDialog(
                    itemName = name.value,
                    onNameChange = { name.value = it },
                    onConfirm = {},
                    onDismiss = { dismissed = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.waitForIdle()
        assert(dismissed)
    }


    @Test
    fun overviewCard_clickTriggersCallback() {
        var clicked = false

        composeTestRule.setContent {
            RiseTheme {
                OverviewCard(title = "Test Card") {
                    clicked = true
                }
            }
        }

        composeTestRule.onNodeWithText("Test Card").performClick()
        composeTestRule.waitForIdle()
        assert(clicked)
    }

    @Test
    fun successDialog_disappearsAfterDelay() {
        var dismissed = false

        composeTestRule.setContent {
            RiseTheme {
                SuccessDialog(onDismiss = { dismissed = true })
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 2000) {
            dismissed
        }
        assert(dismissed)
    }

    @Test
    fun overviewSection_displaysTitleAndCards() {
        val overviews = listOf(
            com.example.riseep3.data.overview.OverviewEntity(1, "Budget", 1, 1000.0, 0.0)
        )
        val createdItems = listOf("Extra" to "Overview")
        var cardClicked: String? = null

        composeTestRule.setContent {
            RiseTheme {
                OverviewSection(
                    overviews = overviews,
                    createdItems = createdItems,
                    onAddClick = {},
                    onCardClick = { cardClicked = it }
                )
            }
        }

        composeTestRule.onNodeWithText("Overviews").assertIsDisplayed()


        composeTestRule.onNodeWithTag("OverviewCard_Extra").performClick()
        composeTestRule.waitForIdle()
        assert(cardClicked == "Extra") {
            "Card click did not register. Expected: Extra, Actual: $cardClicked"
        }
    }
}
