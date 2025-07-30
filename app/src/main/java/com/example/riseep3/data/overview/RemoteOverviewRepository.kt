package com.example.riseep3.data.overview

import com.example.riseep3.data.amount.AmountItemDao
import com.example.riseep3.domain.overview.OverviewDto
import com.example.riseep3.domain.overview.OverviewRequestWrapper
import com.example.riseep3.domain.overview.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.riseep3.domain.overview.flatten

class RemoteOverviewRepository(
    private val overviewDao: OverviewDao,
    private val amountItemDao: AmountItemDao
) : OverviewRepository {

    private val api = RetrofitInstance.overviewApiService

    override fun getAllOverviews(): Flow<List<OverviewEntity>> = flow {
        val response = api.getOverviews()
        val overviewEntities = response.map { it.toEntity() }

        val amountEntities = response.flatMap { overview ->
            overview.amounts.flatMap { it.flatten() }
                .map { it.toEntity() }
        }

        overviewDao.insertAll(overviewEntities)
        amountItemDao.insertAll(amountEntities)

        emit(overviewEntities)
    }


    override fun getOverviewById(id: Int): Flow<OverviewEntity?> = overviewDao.getOverviewById(id)

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        overviews.collect { list ->
            list.forEach { overview ->
                val overviewDto = OverviewDto(
                    id = overview.id,
                    title = overview.title,
                    categoryId = overview.categoryId,
                    totalIncome = overview.totalIncome,
                    result = overview.result,
                    amounts = emptyList()
                )
                api.addOverview(overviewDto)
            }
        }
    }

    override suspend fun update(overview: OverviewEntity) {
        val overviewDto = OverviewDto(
            id = overview.id,
            title = overview.title,
            categoryId = overview.categoryId,
            totalIncome = overview.totalIncome,
            result = overview.result,
            amounts = emptyList()
        )

        api.updateOverview(overview.id, OverviewRequestWrapper(overviewDto))
    }

    override suspend fun updateTotalIncome(id: Int, newTotalIncome: Double) {
        api.updateTotalIncome(id, newTotalIncome)
    }


    override suspend fun delete(overview: OverviewEntity) {
        api.deleteOverview(overview.id)
    }
}
