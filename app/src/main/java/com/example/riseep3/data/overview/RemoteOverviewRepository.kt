package com.example.riseep3.data.overview

import com.example.riseep3.data.amount.AmountItemDao
import com.example.riseep3.domain.overview.OverviewDto
import com.example.riseep3.domain.overview.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.riseep3.domain.amount.AmountWrapper
import com.example.riseep3.domain.overview.flatten

class RemoteOverviewRepository(
    private val overviewDao: OverviewDao,
    private val amountItemDao: AmountItemDao
) : OverviewRepository {

    private val api = RetrofitInstance.overviewApiService

    override fun getAllOverviews(): Flow<List<OverviewEntity>> = flow {
        val response = api.getOverviews()
        val overviewEntities = response.values.map { it.toEntity() }

        val amountEntities = response.values.flatMap { overview ->
            overview.amounts?.`$values`
                ?.flatMap { it.flatten() }
                ?.map { it.toEntity() }
                ?: emptyList()
        }

        overviewDao.insertAll(overviewEntities)
        amountItemDao.insertAll(amountEntities)

        emit(overviewEntities)
    }

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> = overviewDao.getOverviewById(id)

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        overviews.collect { list ->
            list.forEach { overview ->
                api.addOverview(
                    OverviewDto(
                        id = overview.id,
                        title = overview.title,
                        categoryId = overview.categoryId,
                        totalIncome = overview.totalIncome,
                        result = overview.result,
                        amounts = AmountWrapper(emptyList())
                    )
                )
            }
        }
    }

    override suspend fun update(overview: OverviewEntity) {
        api.updateOverview(overview.id, OverviewDto(
            id = overview.id,
            title = overview.title,
            categoryId = overview.categoryId,
            totalIncome = overview.totalIncome,
            result = overview.result,
            amounts = AmountWrapper(emptyList())
        ))
    }

    override suspend fun delete(overview: OverviewEntity) {
        api.deleteOverview(overview.id)
    }
}
