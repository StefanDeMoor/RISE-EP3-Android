package com.example.riseep3.data.amount

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amount_items")
data class AmountItemEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val amount: Double,
    val overviewId: Int,
    val parentAmountItemId: Int?
)
