package com.example.riseep3.domain.overview

import com.example.riseep3.data.amount.AmountItemEntity
import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.domain.amount.AmountItemDto

fun OverviewDto.toEntity(): OverviewEntity = OverviewEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    totalIncome = totalIncome,
    result = result
)

fun AmountItemDto.toEntity(): AmountItemEntity = AmountItemEntity(
    id = id,
    name = name ?: "",
    amount = amount,
    overviewId = overviewId,
    parentAmountItemId = parentAmountItemId
)

fun AmountItemDto.flatten(): List<AmountItemDto> {
    return listOf(this) + (subAmounts.flatMap { it.flatten() } )
}

