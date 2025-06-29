package com.example.riseep3.ui.screens.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class OverviewViewModel : ViewModel() {
    var uiState by mutableStateOf(OverviewState())
        private set

    fun onIncomeChange(newIncome: String) {
        if (!uiState.isIncomeSet) {
            uiState = uiState.copy(
                income = newIncome
            )
        }
    }

    fun onIncomeConfirm() {
        if (uiState.income.isNotBlank()) {
            uiState = uiState.copy(isIncomeSet = true)
        }
    }

}