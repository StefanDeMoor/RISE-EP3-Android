package com.example.riseep3.data.amount

import kotlinx.coroutines.flow.Flow

interface AmountItemCategory {
    fun getAllAmountItem(): Flow<List<AmountItemEntity>>
    suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>)
    suspend fun update(amountItem: AmountItemEntity)
    suspend fun delete(amountItem: AmountItemEntity)
}
