package com.example.riseep3.data.overview

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HybridOverviewRepository(
    private val remote: OverviewRepository,
    private val local: OverviewRepository
) : OverviewRepository {
    override fun getAllOverviews(): Flow<List<OverviewEntity>> = local.getAllOverviews().also {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteItems = remote.getAllOverviews().first()
                val localItems = local.getAllOverviews().first()

                val remoteIds = remoteItems.map { it.id }.toSet()
                val toDelete = localItems.filterNot { it.id in remoteIds }

                toDelete.forEach { local.delete(it) }
                local.insertAll(flowOf(remoteItems))
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

    override suspend fun delete(overview: OverviewEntity) {
        remote.delete(overview)
        local.delete(overview)
    }
}