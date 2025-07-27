package com.example.riseep3.ui.screens.overview

data class OverviewState(
    val baseIncome: Int = 0,
    val income: String = "",
    val isIncomeSet: Boolean = false,
    val isAdjusting: Boolean = false,
    val isAddition: Boolean? = null,
    val amountInput: String = "",
    val amountName: String = "",
    val adjustments: List<Pair<String, Int>> = emptyList(),
    val editIndex: Int? = null
)


