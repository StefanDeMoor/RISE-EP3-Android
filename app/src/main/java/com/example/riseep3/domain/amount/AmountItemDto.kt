package com.example.riseep3.domain.amount

data class AmountItemDto(
    val id: Int,
    val name: String?,
    val amount: Double,
    val overviewId: Int,
    val parentAmountItemId: Int?,
    val subAmounts: AmountWrapper?
)
