package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.amount.AmountItemRepository
import com.example.riseep3.data.overview.OverviewRepository
import com.example.riseep3.ui.screens.overview.OverviewState
import com.example.riseep3.ui.screens.overview.OverviewViewModel

class FakeOverviewViewModel(
    amountItemRepo: AmountItemRepository,
    overviewRepo: OverviewRepository,
    initialState: OverviewState = OverviewState(
        overviewTitle = "Monthly Report",
        totalIncome = 2500.0,
        isTotalIncomeSet = true,
        isAdjusting = false,
        adjustments = listOf(
            Triple(1, "Rent", 1200.0),
            Triple(2, "Groceries", 400.0),
            Triple(3, "Salary Bonus", 500.0)
        ),
        amountName = "",
        amountInput = 2100.00,
        result = 2500.0
    )
) : OverviewViewModel(amountItemRepo, overviewRepo) {

    override var uiState: OverviewState = initialState
}
