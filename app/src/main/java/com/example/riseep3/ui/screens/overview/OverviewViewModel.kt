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
        val signedAmount = if (uiState.isAddition == true) amount else -amount

        val updatedAdjustments = uiState.adjustments.toMutableList()
        val currentIncome = uiState.income.toIntOrNull() ?: 0

        val newAdjustments: List<Pair<String, Int>>
        val newIncome: Int

        if (uiState.editIndex != null) {
            // Bewerken bestaande regel
            val previous = updatedAdjustments[uiState.editIndex!!]
            updatedAdjustments[uiState.editIndex!!] = uiState.amountName to signedAmount
            newIncome = currentIncome - previous.second + signedAmount
        } else {
            // Nieuwe regel toevoegen
            updatedAdjustments.add(uiState.amountName to signedAmount)
            newIncome = currentIncome + signedAmount
        }

        uiState = uiState.copy(
            income = newIncome.toString(),
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
        val toRemove = uiState.adjustments[index]
        val updatedList = uiState.adjustments.toMutableList().apply { removeAt(index) }
        val newIncome = (uiState.income.toIntOrNull() ?: 0) - toRemove.second

        uiState = uiState.copy(
            adjustments = updatedList,
            income = newIncome.toString()
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

