package com.example.riseep3.data.overview

import kotlinx.coroutines.flow.Flow

class OfflineOverviewRepository(private val dao: OverviewDao) : OverviewRepository {
    override fun getAllOverviews(): Flow<List<OverviewEntity>> = dao.getAllOverviews()

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> = dao.getOverviewById(id)

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        overviews.collect { dao.insertAll(it) }
    }

    override suspend fun update(overview: OverviewEntity) = dao.update(overview)

    override suspend fun updateTotalIncome(id: Int, newTotalIncome: Double) {
        dao.updateTotalIncome(id, newTotalIncome)
    }

    override suspend fun delete(overview: OverviewEntity) = dao.delete(overview)
}