package com.example.riseep3.data.amount

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "amount_items",
    foreignKeys = [
        ForeignKey(
            entity = AmountItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentAmountItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AmountItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val amount: Double,
    val overviewId: Int,
    val parentAmountItemId: Int?
)

