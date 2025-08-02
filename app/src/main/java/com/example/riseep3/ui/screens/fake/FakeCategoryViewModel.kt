package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.category.CategoryRepository
import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.data.overview.OverviewRepository
import com.example.riseep3.ui.screens.category.CategoryState
import com.example.riseep3.ui.screens.category.CategoryViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeCategoryViewModel(
    categoryRepo: CategoryRepository,
    overviewRepo: OverviewRepository
) : CategoryViewModel(categoryRepo, overviewRepo) {

    private val _uiState = MutableStateFlow(
        CategoryState(
            selectedCategory = "Overview",
            categories = listOf(
                CategoryEntity(1, "Overview"),
                CategoryEntity(2, "Expenses"),
                CategoryEntity(3, "Income")
            ),
            overviews = listOf(
                OverviewEntity(
                    id = 1,
                    title = "Monthly Report",
                    categoryId = 1,
                    totalIncome = 2100.00,
                    result = 500.00
                ),
                OverviewEntity(
                    id = 2,
                    title = "Yearly Summary",
                    categoryId = 1,
                    totalIncome = 5100.00,
                    result = 1300.00
                )
            ),
            createdItems = listOf(
                "Quarterly Analysis" to "Overview",
                "Holiday Budget" to "Expenses"
            ),
            isDialogOpen = false,
            showSuccessDialog = false,
            newItemName = ""
        )
    )

    override val uiState = _uiState.asStateFlow()
}
