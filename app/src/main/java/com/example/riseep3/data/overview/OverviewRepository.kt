package com.example.riseep3.data.overview

import kotlinx.coroutines.flow.Flow

interface OverviewRepository {
    fun getAllOverviews(): Flow<List<OverviewEntity>>
    fun getOverviewById(id: Int): Flow<OverviewEntity?>
    suspend fun insertAll(overviews: Flow<List<OverviewEntity>>)
    suspend fun update(overview: OverviewEntity)
    suspend fun delete(overview: OverviewEntity)
}