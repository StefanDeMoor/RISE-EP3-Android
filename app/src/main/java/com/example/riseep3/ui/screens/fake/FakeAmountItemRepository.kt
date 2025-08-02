package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.amount.AmountItemCategory
import com.example.riseep3.data.amount.AmountItemEntity
import kotlinx.coroutines.flow.Flow


class FakeAmountItemRepository : AmountItemCategory {
    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> {
        TODO("Not yet implemented")
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