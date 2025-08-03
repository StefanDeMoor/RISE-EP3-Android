package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.amount.AmountItemCategory
import com.example.riseep3.data.amount.AmountItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeAmountItemRepository : AmountItemCategory {

    private val fakeAmountItems = listOf(
        AmountItemEntity(
            id = 1,
            overviewId = 1,
            amount = 1000.0,
            name = "Huur",
            parentAmountItemId = null
        ),
        AmountItemEntity(
            id = 2,
            overviewId = 1,
            amount = 500.0,
            name = "Elek",
            parentAmountItemId = null
        )
    )

    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> {
        return flow {
            emit(fakeAmountItems.filter { it.overviewId == 1 })
        }
    }

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(amountItem: AmountItemEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(amountItem: AmountItemEntity) {
        TODO("Not yet implemented")
    }


}