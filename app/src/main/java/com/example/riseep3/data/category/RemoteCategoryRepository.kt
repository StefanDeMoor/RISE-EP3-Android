package com.example.riseep3.data.category

import android.util.Log
import com.example.riseep3.domain.category.toDto
import com.example.riseep3.domain.category.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteCategoryRepository : CategoryRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> =
        flow {
            val response = RetrofitInstance.categoryApiService.getCategories()
            if (response.isSuccessful) {
                Log.d("RemoteCategoryRepo", "getAllCategories success: Response code = ${response.code()}")
                val categoryEntities = response.body()?.map { it.toEntity() } ?: emptyList()
                emit(categoryEntities)
            } else {
                Log.e("RemoteCategoryRepo", "API error: Response code = ${response.code()} ${response.message()}")
                emit(emptyList())
            }
        }.catch { e ->
            Log.e("RemoteCategoryRepo", "Network error", e)
            emit(emptyList())
        }

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> =
        flow {
            val response = RetrofitInstance.categoryApiService.getCategories()
            if (response.isSuccessful) {
                Log.d("RemoteCategoryRepo", "getCategoryById success: Response code = ${response.code()}")
                val category = response.body()?.find { it.id == id }?.toEntity()
                emit(category)
            } else {
                Log.e("RemoteCategoryRepo", "Error: Response code = ${response.code()} getting category")
                emit(null)
            }
        }.catch { e ->
            Log.e("RemoteCategoryRepo", "Exception", e)
            emit(null)
        }

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        categories.collect { list ->
            list.forEach {
                val response = RetrofitInstance.categoryApiService.addCategory(it.toDto())
                if (response.isSuccessful) {
                    Log.d("RemoteCategoryRepo", "Insert success: Response code = ${response.code()}")
                } else {
                    Log.e("RemoteCategoryRepo", "Insert failed: Response code = ${response.code()}")
                }
            }
        }
    }

    override suspend fun delete(category: CategoryEntity) {
        val response = RetrofitInstance.categoryApiService.deleteCategory(category.id)
        if (response.isSuccessful) {
            Log.d("RemoteCategoryRepo", "Delete success: Response code = ${response.code()}")
        } else {
            Log.e("RemoteCategoryRepo", "Delete failed: Response code = ${response.code()}")
        }
    }

    override suspend fun update(category: CategoryEntity) {
        val response = RetrofitInstance.categoryApiService.updateCategory(category.id, category.toDto())
        if (response.isSuccessful) {
            Log.d("RemoteCategoryRepo", "Update success: Response code = ${response.code()}")
        } else {
            Log.e("RemoteCategoryRepo", "Update failed: Response code = ${response.code()}")
        }
    }
}
