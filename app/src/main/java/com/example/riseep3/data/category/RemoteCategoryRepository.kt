package com.example.riseep3.data.category

import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteCategoryRepository : CategoryRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> = flow {
        emit(RetrofitInstance.categoryApiService.getCategories())
    }

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> = flow {
        val categories = RetrofitInstance.categoryApiService.getCategories()
        emit(categories.find { it.id == id })
    }

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        categories.collect { list ->
            list.forEach { RetrofitInstance.categoryApiService.addCategory(it) }
        }
    }

    override suspend fun delete(category: CategoryEntity) {
        RetrofitInstance.categoryApiService.deleteCategory(category.id)
    }

    override suspend fun update(category: CategoryEntity) {
        RetrofitInstance.categoryApiService.updateCategory(category.id, category)
    }
}