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
        val incomeValue = uiState.income.toIntOrNull()
        if (incomeValue != null) {
            uiState = uiState.copy(
                isIncomeSet = true,
                baseIncome = incomeValue
            )
        }
    }


    fun onAdjustmentStart(isAddition: Boolean) {
        uiState = uiState.copy(
            isAdjusting = true,
            isAddition = isAddition,
            amountInput = "",
            amountName = "",
            editIndex = null
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
        val signedAmount = if (uiState.isAddition == true) -amount else amount

        val updatedAdjustments = uiState.adjustments.toMutableList()

        if (uiState.editIndex != null) {
            updatedAdjustments[uiState.editIndex!!] = uiState.amountName to signedAmount
        } else {
            updatedAdjustments.add(uiState.amountName to signedAmount)
        }

        val totalAdjustments = updatedAdjustments.sumOf { it.second }
        if (totalAdjustments > uiState.baseIncome) {
            return
        }

        uiState = uiState.copy(
            adjustments = updatedAdjustments,
            isAdjusting = false,
            isAddition = null,
            amountInput = "",
            amountName = "",
            editIndex = null
        )
    }


    fun onEditStart(index: Int) {
        val (name, value) = uiState.adjustments[index]
        uiState = uiState.copy(
            isAdjusting = true,
            isAddition = value >= 0,
            amountInput = kotlin.math.abs(value).toString(),
            amountName = name,
            editIndex = index
        )
    }

    fun onDeleteAdjustment(index: Int) {
        val updatedList = uiState.adjustments.toMutableList().apply { removeAt(index) }

        uiState = uiState.copy(
            adjustments = updatedList
        )
    }


    fun onIncomeEditStart() {
        uiState = uiState.copy(
            isIncomeSet = false
        )
    }

    fun onIncomeDelete() {
        uiState = uiState.copy(
            income = "",
            isIncomeSet = false,
            adjustments = emptyList()
        )
    }

}

