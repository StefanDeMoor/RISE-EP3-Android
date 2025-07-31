package com.example.riseep3.domain.amount

import com.example.riseep3.data.amount.AmountItemEntity

fun AmountItemDto.toEntity(): AmountItemEntity = AmountItemEntity(
    id = id,
    name = name ?: "",
    amount = amount,
    overviewId = overviewId,
    parentAmountItemId = parentAmountItemId
)

fun AmountItemDto.flatten(): List<AmountItemDto> {
    return listOf(this) + subAmounts.flatMap { it.flatten() }
}

fun List<AmountItemDto>.flattenAll(): List<AmountItemDto> {
    return this.flatMap { it.flatten() }
}