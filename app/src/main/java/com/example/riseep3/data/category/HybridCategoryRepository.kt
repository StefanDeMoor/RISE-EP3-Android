package com.example.riseep3.data.category

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HybridCategoryRepository(
    private val remote: RemoteCategoryRepository,
    private val local: OfflineCategoryRepository
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> = local.getAllCategories().also {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = remote.getAllCategories().first()

                // ✅ Log what you received from the API
                Log.d("HybridCategoryRepo", "Fetched categories from API: $categories")

                local.insertAll(flowOf(categories))
            } catch (e: Exception) {
                Log.e("HybridCategoryRepo", "Error fetching from API, using cache", e)
            }
        }
    }

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> = local.getCategoryById(id)

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        remote.insertAll(categories)
        local.insertAll(categories)
    }

    override suspend fun delete(category: CategoryEntity) {
        remote.delete(category)
        local.delete(category)
    }

    override suspend fun update(category: CategoryEntity) {
        remote.update(category)
        local.update(category)
    }
}
