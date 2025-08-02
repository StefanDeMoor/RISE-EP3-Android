package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.flow.Flow

class FakeOverviewRepository : OverviewRepository {
    override fun getAllOverviews(): Flow<List<OverviewEntity>> {
        TODO("Not yet implemented")
    }

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(overview: OverviewEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTotalIncome(id: Int, newTotalIncome: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(overview: OverviewEntity) {
        TODO("Not yet implemented")
    }
}