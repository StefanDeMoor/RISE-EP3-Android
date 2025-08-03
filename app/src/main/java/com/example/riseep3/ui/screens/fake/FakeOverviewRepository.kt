package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOverviewRepository : OverviewRepository {

    private val fakeData = listOf(
        OverviewEntity(
            id = 1,
            title = "August Budget",
            totalIncome = 3000.0,
            categoryId = 1,
            result = 0.0
        ),
        OverviewEntity(
            id = 2,
            title = "September Budget",
            totalIncome = 3200.0,
            categoryId = 1,
            result = 0.0
        )
    )

    override fun getAllOverviews(): Flow<List<OverviewEntity>> {
        TODO("Not yet implemented")
    }

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> {
        return flow {
            emit(fakeData.find { it.id == id })
        }
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