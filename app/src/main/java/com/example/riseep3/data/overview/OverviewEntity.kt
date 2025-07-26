package com.example.riseep3.data.overview

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "overviews",
    foreignKeys = [
        ForeignKey(
            entity = com.example.riseep3.data.category.CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OverviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val categoryId: Int,
    val totalIncome: Double,
    val result: Double
)