package com.example.riseep3.ui.screens.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.riseep3.MainApplication
import com.example.riseep3.data.amount.AmountItemCategory
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val amountItemRepo: AmountItemCategory,
    private val overviewRepo: OverviewRepository
) : ViewModel() {
    var uiState by mutableStateOf(OverviewState())
        private set

    fun loadOverviewById(id: Int) {
        viewModelScope.launch {
            overviewRepo.getOverviewById(id).collect { overview ->
                val isTotalIncomeSet = overview?.totalIncome != null
                uiState = uiState.copy(
                    totalIncome = overview?.totalIncome ?: 0.0,
                    isTotalIncomeSet = isTotalIncomeSet,
                    result = overview?.totalIncome ?: 0.0
                )
            }
        }
    }

    fun onIncomeChange(newTotalIncome: Double) {
        if (!uiState.isTotalIncomeSet) {
            uiState = uiState.copy(totalIncome = newTotalIncome)
        }
    }

    fun onIncomeConfirm() {
        val totalIncomeValue = uiState.totalIncome
        if (totalIncomeValue != null) {
//            viewModelScope.launch {
//                overviewRepo.updateTotalIncome(incomeValue)
//            }
            uiState = uiState.copy(
                isTotalIncomeSet = true,
                result = totalIncomeValue
            )
        }
    }


    fun onAdjustmentStart(isAddition: Boolean) {
        uiState = uiState.copy(
            isAdjusting = true,
            isAddition = isAddition,
            amountInput = 0.0,
            amountName = "",
            editIndex = null
        )
    }

    fun onAmountChange(newAmount: Double) {
        uiState = uiState.copy(amountInput = newAmount)
    }

    fun onAmountNameChange(newName: String) {
        uiState = uiState.copy(amountName = newName)
    }

    fun onAmountConfirm() {
        val amount = uiState.amountInput
        val signedAmount = if (uiState.isAddition == true) -amount else amount

        val updatedAdjustments = uiState.adjustments.toMutableList()

        if (uiState.editIndex != null) {
            updatedAdjustments[uiState.editIndex!!] = uiState.amountName to signedAmount
        } else {
            updatedAdjustments.add(uiState.amountName to signedAmount)
        }

        val totalAdjustments = updatedAdjustments.sumOf { it.second }
        if (totalAdjustments > uiState.result) {
            return
        }

        uiState = uiState.copy(
            adjustments = updatedAdjustments,
            isAdjusting = false,
            isAddition = null,
            amountInput = 0.0,
            amountName = "",
            editIndex = null
        )
    }


    fun onEditStart(index: Int) {
        val (name, value) = uiState.adjustments[index]
        uiState = uiState.copy(
            isAdjusting = true,
            isAddition = value >= 0,
            amountInput = value,
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
            isTotalIncomeSet = false
        )
    }

    fun onIncomeDelete() {
        uiState = uiState.copy(
            totalIncome = 0.0,
            isTotalIncomeSet = false,
            adjustments = emptyList()
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication
                OverviewViewModel(app.container.amountItemRepository, app.container.overviewRepository)
            }
        }
    }
}

