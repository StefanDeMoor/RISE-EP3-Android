package com.example.riseep3.data.overview

import android.util.Log
import com.example.riseep3.data.amount.AmountItemDao
import com.example.riseep3.domain.overview.OverviewDto
import com.example.riseep3.domain.overview.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import com.example.riseep3.domain.overview.flatten

class RemoteOverviewRepository(
    private val overviewDao: OverviewDao,
    private val amountItemDao: AmountItemDao
) : OverviewRepository {

    private val api = RetrofitInstance.overviewApiService

    override fun getAllOverviews(): Flow<List<OverviewEntity>> = flow {
        val response = api.getOverviews()
        if (response.isSuccessful) {
            Log.d("RemoteOverviewRepo", "getOverviews success: Response code = ${response.code()}")
            val overviewDtos = response.body() ?: emptyList()
            val overviewEntities = overviewDtos.map { it.toEntity() }

            val amountEntities = overviewDtos.flatMap { overview ->
                overview.amounts.flatMap { it.flatten() }.map { it.toEntity() }
            }

            overviewDao.insertAll(overviewEntities)
            amountItemDao.insertAll(amountEntities)

            emit(overviewEntities)
        } else {
            Log.e("RemoteOverviewRepo", "getAllOverviews failed: Response code = ${response.code()}")
            emit(emptyList())
        }
    }.catch { e ->
        Log.e("RemoteOverviewRepo", "Unexpected error", e)
        emit(emptyList())
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
                try {
                    val response = api.addOverview(overviewDto)
                    Log.d("RemoteOverviewRepo", "Insert success: Response code = ${response.code()}")
                } catch (e: Exception) {
                    Log.e("RemoteOverviewRepo", "Insert failed", e)
                }
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
        try {
            val response = api.updateOverview(overview.id, overviewDto)
            Log.d("RemoteOverviewRepo", "Update success: Response code = ${response.code()}")
        } catch (e: Exception) {
            Log.e("RemoteOverviewRepo", "Update failed", e)
        }
    }

    override suspend fun updateTotalIncome(id: Int, newTotalIncome: Double) {
        try {
            val response = api.updateTotalIncome(id, newTotalIncome)
            if (response.isSuccessful) {
                Log.d("RemoteOverviewRepo", "updateTotalIncome success: Response code = ${response.code()}")
            } else {
                Log.e("RemoteOverviewRepo", "updateTotalIncome failed: Response code = ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("RemoteOverviewRepo", "Exception in updateTotalIncome", e)
        }
    }

    override suspend fun delete(overview: OverviewEntity) {
        try {
            val response = api.deleteOverview(overview.id)
            Log.d("RemoteOverviewRepo", "Delete success: Response code = ${response.code()}")
        } catch (e: Exception) {
            Log.e("RemoteOverviewRepo", "Delete failed", e)
        }
    }
}
