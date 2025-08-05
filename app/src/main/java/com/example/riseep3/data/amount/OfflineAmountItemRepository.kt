package com.example.riseep3.data.amount

import kotlinx.coroutines.flow.Flow

class OfflineAmountItemRepository(private val dao: AmountItemDao) : AmountItemRepository {
    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> = dao.getAllAmountItems()

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        amountItems.collect { dao.insertAll(it) }
    }

    override suspend fun update(amountItem: AmountItemEntity) = dao.update(amountItem)

    override suspend fun delete(amountItem: AmountItemEntity) = dao.delete(amountItem)
}