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
import com.example.riseep3.data.amount.AmountItemEntity
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val amountItemRepo: AmountItemCategory,
    private val overviewRepo: OverviewRepository
) : ViewModel() {
    var uiState by mutableStateOf(OverviewState())
        private set

    private var currentOverviewId: Int = -1

    fun loadOverviewById(id: Int) {
        viewModelScope.launch {
            currentOverviewId = id

            launch {
                overviewRepo.getOverviewById(id).collect { overview ->
                    val isTotalIncomeSet = overview?.totalIncome != null
                    if (overview != null) {
                        uiState = uiState.copy(
                            totalIncome = overview.totalIncome ?: 0.0,
                            isTotalIncomeSet = isTotalIncomeSet,
                            overviewTitle = overview.title,
                            result = overview.totalIncome ?: 0.0
                        )
                    }
                }
            }

            launch {
                amountItemRepo.getAllAmountItem().collect { items ->
                    val relevantItems = items.filter { it.overviewId == id }
                    val adjustments = relevantItems.map { it.name to it.amount }

                    uiState = uiState.copy(
                        adjustments = adjustments
                    )
                }
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
        viewModelScope.launch {
            if (currentOverviewId != -1) {
                overviewRepo.updateTotalIncome(currentOverviewId, totalIncomeValue)
                uiState = uiState.copy(
                    isTotalIncomeSet = true,
                    result = totalIncomeValue
                )
            }
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

        if (currentOverviewId == -1 || uiState.amountName.isBlank()) return

        val newItem = AmountItemEntity(
            id = 0,
            name = uiState.amountName,
            amount = signedAmount,
            overviewId = currentOverviewId,
            parentAmountItemId = null
        )

        viewModelScope.launch {
            amountItemRepo.insertAll(flowOf(listOf(newItem)))
        }

        val updatedAdjustments = uiState.adjustments.toMutableList().apply {
            add(uiState.amountName to signedAmount)
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
        val adjustmentToDelete = uiState.adjustments.getOrNull(index) ?: return

        viewModelScope.launch {
            amountItemRepo.getAllAmountItem().collect { items ->
                val matchingItem = items.firstOrNull {
                    it.overviewId == currentOverviewId &&
                            it.name == adjustmentToDelete.first &&
                            it.amount == adjustmentToDelete.second
                }

                if (matchingItem != null) {
                    amountItemRepo.delete(matchingItem)

                    val updatedList = uiState.adjustments.toMutableList().apply {
                        removeAt(index)
                    }
                    uiState = uiState.copy(adjustments = updatedList)
                }
            }
        }
    }



    fun onTotalIncomeEditStart() {
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

