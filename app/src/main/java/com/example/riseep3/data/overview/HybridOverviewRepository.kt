package com.example.riseep3.data.overview

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HybridOverviewRepository(
    private val remote: RemoteOverviewRepository,
    private val local: OfflineOverviewRepository
) : OverviewRepository {
    override fun getAllOverviews(): Flow<List<OverviewEntity>> = local.getAllOverviews().also {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteOverviews = remote.getAllOverviews().first()
                Log.d("HybridOverviewRepo", "Fetched overviews from API: $remoteOverviews")
                val localOverviews = local.getAllOverviews().first()

                val remoteIds = remoteOverviews.map { it.id }.toSet()
                val overviewsToDelete = localOverviews.filterNot { it.id in remoteIds }

                overviewsToDelete.forEach { local.delete(it) }
                local.insertAll(flowOf(remoteOverviews))
            } catch (e: Exception) {
                Log.e("HybridOverviewRepo", "Using cache", e)
            }
        }
    }

    override fun getOverviewById(id: Int): Flow<OverviewEntity?> = local.getOverviewById(id)

    override suspend fun insertAll(overviews: Flow<List<OverviewEntity>>) {
        remote.insertAll(overviews)
        local.insertAll(overviews)
    }

    override suspend fun update(overview: OverviewEntity) {
        remote.update(overview)
        local.update(overview)
    }

    override suspend fun updateTotalIncome(id: Int, newTotalIncome: Double) {
        // 1. Update remote first
        remote.updateTotalIncome(id, newTotalIncome)

        // 2. Update local cache: get current overview, update the field, save
        val overview = local.getOverviewById(id).first()
        if (overview != null) {
            val updatedOverview = overview.copy(totalIncome = newTotalIncome)
            local.update(updatedOverview)
        }
    }


    override suspend fun delete(overview: OverviewEntity) {
        remote.delete(overview)
        local.delete(overview)
    }
}