package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.amount.AmountItemCategory
import com.example.riseep3.data.amount.AmountItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeAmountItemRepository : AmountItemCategory {

    private val fakeAmountItems = mutableListOf<AmountItemEntity>()

    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> {
        return flow {
            emit(fakeAmountItems)
        }
    }

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        amountItems.collect {
            fakeAmountItems.addAll(it)
        }
    }

    override suspend fun update(amountItem: AmountItemEntity) {
        val index = fakeAmountItems.indexOfFirst { it.id == amountItem.id }
        if (index != -1) {
            fakeAmountItems[index] = amountItem
        }
    }

    override suspend fun delete(amountItem: AmountItemEntity) {
        fakeAmountItems.removeIf { it.id == amountItem.id }
    }

    fun reset() {
        fakeAmountItems.clear()
    }
}
