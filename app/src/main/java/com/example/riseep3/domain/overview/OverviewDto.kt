package com.example.riseep3.domain.overview

import com.example.riseep3.domain.amount.AmountItemDto

data class OverviewDto(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val totalIncome: Double?,
    val result: Double,
    val amounts: List<AmountItemDto>
)
