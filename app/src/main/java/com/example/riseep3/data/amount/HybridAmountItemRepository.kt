package com.example.riseep3.data.amount

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HybridAmountItemRepository(
    private val remote : RemoteAmountItemRepository,
    private val local : OfflineAmountItemRepository
) : AmountItemCategory {

    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> = local.getAllAmountItem().also {
       CoroutineScope(Dispatchers.IO).launch {
           try {
               val remoteAmountItems = remote.getAllAmountItem().first()
               Log.d("HybridAmountItemRepo", "Fetched amountItems from API: $remoteAmountItems")
               val localAmountItems = local.getAllAmountItem().first()

               val remoteIds = remoteAmountItems.map { it.id }.toSet()
               val overviewsToDelete = localAmountItems.filterNot { it.id in remoteIds }

               overviewsToDelete.forEach { local.delete(it) }
               local.insertAll(flowOf(remoteAmountItems))
           } catch (e: Exception) {
               Log.e("HybridAmountItemRepo", "Using cache", e)
           }
       }
    }

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        remote.insertAll(amountItems)
        local.insertAll(amountItems)
    }

    override suspend fun delete(amountItem: AmountItemEntity) {
        TODO("Not yet implemented")
    }
}