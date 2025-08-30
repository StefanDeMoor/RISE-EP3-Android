package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.data.overview.OverviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOverviewRepository : OverviewRepository {

    private val fakeData = mutableListOf<OverviewEntity>()

    override fun getAllOverviews(): Flow<List<OverviewEntity>> = flow {
        emit(fakeData)
    }

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> = flow {
        emit(fakeData.find { it.id == id })
    }

    override suspend fun insert(overview: OverviewEntity) {
        fakeData.add(overview)
    }

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        overviews.collect { list ->
            list.forEach { newOverview ->
                val index = fakeData.indexOfFirst { it.id == newOverview.id }
                if (index != -1) {
                    fakeData[index] = newOverview
                } else {
                    fakeData.add(newOverview)
                }
            }
        }
    }

    override suspend fun update(overview: OverviewEntity) {
        val index = fakeData.indexOfFirst { it.id == overview.id }
        if (index != -1) {
            fakeData[index] = overview
        }
    }

    override suspend fun updateTotalIncome(id: Int, newTotalIncome: Double) {
        val index = fakeData.indexOfFirst { it.id == id }
        if (index != -1) {
            val old = fakeData[index]
            fakeData[index] = old.copy(totalIncome = newTotalIncome)
        }
    }

    override suspend fun delete(overview: OverviewEntity) {
        fakeData.removeIf { it.id == overview.id }
    }

    fun reset() {
        fakeData.clear()
    }
}
