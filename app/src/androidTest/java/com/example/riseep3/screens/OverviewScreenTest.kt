package com.example.riseep3.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.riseep3.ui.componenten.overview.*
import com.example.riseep3.ui.screens.fake.FakeAmountItemRepository
import com.example.riseep3.ui.screens.fake.FakeOverviewRepository
import com.example.riseep3.ui.screens.fake.FakeOverviewViewModel
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
import com.example.riseep3.ui.screens.overview.OverviewScreen
import com.example.riseep3.ui.screens.overview.OverviewState
import org.junit.Rule
import org.junit.Test

class OverviewScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeAmountItemRepo = FakeAmountItemRepository()
    private val fakeOverviewRepo = FakeOverviewRepository()
    private val fakeThemeViewModel = FakeThemeViewModel()

    @Test
    fun overviewScreen_displaysTitleAndTotalIncomeInputFieldInitially() {
        val fakeViewModel = FakeOverviewViewModel(
            amountItemRepo = fakeAmountItemRepo,
            overviewRepo = fakeOverviewRepo,
            initialState = OverviewState(
                overviewTitle = "Test Overzicht",
                totalIncome = 3000.0,
                isTotalIncomeSet = false,
                isAdjusting = false,
                adjustments = emptyList()
            )
        )
        composeTestRule.setContent {
            OverviewScreen(
                overviewId = 1,
                viewModel = fakeViewModel,
                themeViewModel = fakeThemeViewModel
            )
        }

        composeTestRule.onNodeWithTag("OverviewScreenTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("TotalIncomeInputField").assertIsDisplayed()
    }

    @Test
    fun totalIncomeInputField_acceptsInputAndConfirm() {
        composeTestRule.setContent {
            TotalIncomeInputField(
                totalIncome = 0.0,
                onTotalIncomeChange = {},
                onConfirm = {}
            )
        }

        val input = composeTestRule.onNodeWithText("Totaal Inkomen")
        input.performTextInput("2500")
        input.assertTextContains("2500")
    }

    @Test
    fun totalIncomeSummaryCard_displaysCorrectIncomeAndRespondsToClick() {
        var clicked = false

        composeTestRule.setContent {
            TotalIncomeSummaryCard(
                totalIncome = 1234.56,
                onEdit = { clicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("Inkomen bewerken").performClick()
        assert(clicked)
    }

    @Test
    fun adjustmentButtons_triggersCorrectCallbacks() {
        var subtracted = false
        var added = false

        composeTestRule.setContent {
            AdjustmentButtons(
                onSubtract = { subtracted = true },
                onAdd = { added = true }
            )
        }

        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("+").performClick()

        assert(subtracted)
        assert(added)
    }

    @Test
    fun adjustmentList_displaysItemsAndRespondsToEditAndDelete() {
        val testList = listOf(
            Triple(0, "Lunch", -10.0),
            Triple(1, "Salary", 1000.0)
        )

        var editedIndex = -1
        var deletedIndex = -1

        composeTestRule.setContent {
            AdjustmentList(
                adjustments = testList,
                onEdit = { editedIndex = it },
                onDelete = { deletedIndex = it }
            )
        }

        composeTestRule.onAllNodesWithContentDescription("Bewerk")[0].performClick()
        composeTestRule.onAllNodesWithContentDescription("Verwijder")[1].performClick()

        assert(editedIndex == 0)
        assert(deletedIndex == 1)
    }

    @Test
    fun adjustmentInputFields_acceptsNameAndAmountAndConfirm() {
        var name = ""
        var amount = 0.0
        var confirmed = false

        composeTestRule.setContent {
            AdjustmentInputFields(
                amountName = "",
                onAmountNameChange = { name = it },
                amountInput = 0.0,
                onAmountChange = { amount = it },
                onConfirm = { confirmed = true }
            )
        }

        composeTestRule.onNodeWithTag("AmountNameInput").performTextInput("Groceries")
        composeTestRule.onNodeWithTag("AmountInput").performTextInput("15.5")
        composeTestRule.onNodeWithTag("ConfirmButton").performClick()

        assert(name == "Groceries")
        assert(amount == 15.5)
        assert(confirmed)
    }

    @Test
    fun resultOutlinedField_displaysFormattedResult() {
        composeTestRule.setContent {
            ResultOutlinedField(result = 1234.56)
        }

        composeTestRule.onNodeWithText("€1234.56").assertIsDisplayed()
    }
}