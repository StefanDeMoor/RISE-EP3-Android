package com.example.riseep3.data.overview

import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteOverviewRepository : OverviewRepository {
    override fun getAllOverviews(): Flow<List<OverviewEntity>> = flow {
        emit(RetrofitInstance.overviewApiService.getOverviews())
    }

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> = flow {
        val all = RetrofitInstance.overviewApiService.getOverviews()
        emit(all.find { it.id == id })
    }

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        overviews.collect { list ->
            list.forEach { RetrofitInstance.overviewApiService.addOverview(it) }
        }
    }

    override suspend fun update(overview: OverviewEntity) {
        RetrofitInstance.overviewApiService.updateOverview(overview.id, overview)
    }

    override suspend fun delete(overview: OverviewEntity) {
        RetrofitInstance.overviewApiService.deleteOverview(overview.id)
    }
}
