package com.example.riseep3.data.amount

import android.util.Log
import com.example.riseep3.domain.amount.AmountItemDto
import com.example.riseep3.domain.amount.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteAmountItemRepository(
    private val amountItemDao: AmountItemDao
) : AmountItemRepository {

    private val api = RetrofitInstance.amountItemApiService

    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> = flow {
        val response = api.getAmountItems()
        if (response.isSuccessful) {
            Log.d("RemoteAmountItemRepo", "getAllAmountItem success: Response code = ${response.code()}")
            val amountDtos = response.body() ?: emptyList()
            emit(amountDtos.map { it.toEntity() })
        } else {
            Log.e("RemoteAmountItemRepo", "getAllAmountItem failed: Response code = ${response.code()}")
            emit(emptyList())
        }
    }.catch { e ->
        Log.e("RemoteAmountItemRepo", "Unexpected error", e)
        emit(emptyList())
    }

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        amountItems.collect { list ->
            list.forEach { amountItem ->
                val dto = AmountItemDto(
                    id = amountItem.id,
                    name = amountItem.name,
                    amount = amountItem.amount,
                    overviewId = amountItem.overviewId,
                    parentAmountItemId = amountItem.parentAmountItemId,
                    subAmounts = emptyList()
                )
                try {
                    val response = api.addAmountItem(dto)
                    Log.d("RemoteAmountItemRepo", "Insert success: Response code = ${response.code()}")
                } catch (e: Exception) {
                    Log.e("RemoteAmountItemRepo", "Insert failed", e)
                }
            }
        }
    }

    override suspend fun update(amountItem: AmountItemEntity) {
        try {
            val subAmountEntities = amountItemDao.getSubAmounts(amountItem.id)
            val subDtos = subAmountEntities.map { toDtoWithSubAmounts(it) }

            val dto = AmountItemDto(
                id = amountItem.id,
                name = amountItem.name,
                amount = amountItem.amount,
                overviewId = amountItem.overviewId,
                parentAmountItemId = amountItem.parentAmountItemId,
                subAmounts = subDtos
            )

            val response = api.updateAmountItem(amountItem.id, dto)
            if (response.isSuccessful) {
                Log.d("RemoteAmountItemRepo", "Update success: Response code = ${response.code()}")
            } else {
                Log.e("RemoteAmountItemRepo", "Update failed: Response code = ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("RemoteAmountItemRepo", "Exception in update", e)
        }
    }

    private suspend fun toDtoWithSubAmounts(entity: AmountItemEntity): AmountItemDto {
        val subEntities = amountItemDao.getSubAmounts(entity.id)
        val subDtos = subEntities.map { toDtoWithSubAmounts(it) }

        return AmountItemDto(
            id = entity.id,
            name = entity.name,
            amount = entity.amount,
            overviewId = entity.overviewId,
            parentAmountItemId = entity.parentAmountItemId,
            subAmounts = subDtos
        )
    }

    override suspend fun delete(amountItem: AmountItemEntity) {
        try {
            val response = api.deleteAmountItem(amountItem.id)
            if (response.isSuccessful) {
                Log.d("RemoteAmountItemRepo", "Delete success: Response code = ${response.code()}")
            } else {
                Log.e("RemoteAmountItemRepo", "Delete failed: Response code = ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("RemoteAmountItemRepo", "Exception in delete", e)
        }
    }
}
