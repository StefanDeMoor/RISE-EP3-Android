package com.example.riseep3.ui.screens.overview

data class OverviewState(
    val result: Double = 0.0,
    val overviewTitle: String = "",
    val totalIncome: Double = 0.0,
    val isTotalIncomeSet: Boolean = false,
    val isAdjusting: Boolean = false,
    val isAddition: Boolean? = null,
    val amountInput: Double = 0.0,
    val amountName: String = "",
    val adjustments: List<Pair<String, Double>> = emptyList(),
    val editIndex: Int? = null
)


