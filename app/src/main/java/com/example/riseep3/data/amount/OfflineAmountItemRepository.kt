package com.example.riseep3.data.amount

import kotlinx.coroutines.flow.Flow

class OfflineAmountItemRepository(private val dao: AmountItemDao) : AmountItemCategory {
    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> = dao.getAllAmountItems()

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        amountItems.collect { dao.insertAll(it) }
    }

    override suspend fun delete(amountItem: AmountItemEntity) = dao.delete(amountItem)
}