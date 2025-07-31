package com.example.riseep3.data.amount

import com.example.riseep3.domain.amount.AmountItemDto
import com.example.riseep3.domain.amount.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteAmountItemRepository(
    private val amountItemDao: AmountItemDao
) : AmountItemCategory {

    private val api = RetrofitInstance.amountItemApiService

    override fun getAllAmountItem(): Flow<List<AmountItemEntity>> = flow {
        val response = api.getAmountItems()
        val entities = response.map { it.toEntity() }
        emit(entities)
    }

    override suspend fun insertAll(amountItems: Flow<List<AmountItemEntity>>) {
        amountItems.collect { list ->
            list.forEach { amountItem ->
                val amountItemDto = AmountItemDto(
                    id = amountItem.id,
                    name = amountItem.name,
                    amount = amountItem.amount,
                    overviewId = amountItem.overviewId,
                    parentAmountItemId = amountItem.parentAmountItemId,
                    subAmounts = emptyList()
                )
                api.addAmountItem(amountItemDto)
            }
        }
    }

    override suspend fun update(amountItem: AmountItemEntity) {
        val subAmountEntities = amountItemDao.getSubAmounts(amountItem.id)

        val subAmountDtos = subAmountEntities.map { subEntity ->
            toDtoWithSubAmounts(subEntity)
        }

        val amountItemDto = AmountItemDto(
            id = amountItem.id,
            name = amountItem.name,
            amount = amountItem.amount,
            overviewId = amountItem.overviewId,
            parentAmountItemId = amountItem.parentAmountItemId,
            subAmounts = subAmountDtos
        )

        api.updateAmountItem(amountItem.id, amountItemDto)
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
        api.deleteAmountItem(amountItem.id)
    }
}