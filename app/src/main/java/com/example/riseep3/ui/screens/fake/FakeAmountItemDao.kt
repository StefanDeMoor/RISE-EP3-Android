package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.amount.AmountItemDao
import com.example.riseep3.data.amount.AmountItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAmountItemDao : AmountItemDao {

    private val items = mutableListOf<AmountItemEntity>()
    private val stateFlow = MutableStateFlow(items.toList())

    override fun getAllAmountItems(): Flow<List<AmountItemEntity>> = stateFlow

    override suspend fun getSubAmounts(parentId: Int): List<AmountItemEntity> {
        return items.filter { it.parentAmountItemId == parentId }
    }

    override suspend fun insertAll(amountItems: List<AmountItemEntity>) {
        items.addAll(amountItems)
        stateFlow.value = items.toList()
    }

    override suspend fun update(amountItem: AmountItemEntity) {
        val index = items.indexOfFirst { it.id == amountItem.id }
        if (index != -1) {
            items[index] = amountItem
            stateFlow.value = items.toList()
        }
    }

    override suspend fun delete(amountItem: AmountItemEntity) {
        items.removeIf { it.id == amountItem.id }
        stateFlow.value = items.toList()
    }
}
