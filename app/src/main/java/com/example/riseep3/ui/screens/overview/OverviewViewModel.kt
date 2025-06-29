package com.example.riseep3.ui.screens.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OverviewViewModel : ViewModel() {
    var uiState by mutableStateOf(OverviewState())
        private set

    fun onIncomeChange(newIncome: String) {
        if (!uiState.isIncomeSet) {
            uiState = uiState.copy(income = newIncome)
        }
    }

    fun onIncomeConfirm() {
        if (uiState.income.isNotBlank()) {
            uiState = uiState.copy(isIncomeSet = true)
        }
    }

    fun onAdjustmentStart(isAddition: Boolean) {
        uiState = uiState.copy(
            isAdjusting = true,
            isAddition = isAddition,
            amountInput = ""
        )
    }

    fun onAmountChange(newAmount: String) {
        uiState = uiState.copy(amountInput = newAmount)
    }

    fun onAmountNameChange(newName: String) {
        uiState = uiState.copy(amountName = newName)
    }

    fun onAmountConfirm() {
        val amount = uiState.amountInput.toIntOrNull() ?: return
        val signedAmount = if (uiState.isAddition == true) amount else -amount

        val newAdjustments = uiState.adjustments + (uiState.amountName to signedAmount)
        val newIncome = (uiState.income.toIntOrNull() ?: 0) + signedAmount

        uiState = uiState.copy(
            income = newIncome.toString(),
            adjustments = newAdjustments,
            isAdjusting = false,
            isAddition = null,
            amountInput = "",
            amountName = ""
        )
    }
}
