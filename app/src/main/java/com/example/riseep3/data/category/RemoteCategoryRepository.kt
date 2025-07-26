package com.example.riseep3.data.category

import com.example.riseep3.domain.category.toDto
import com.example.riseep3.domain.category.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteCategoryRepository : CategoryRepository {

    override fun getAllCategories(): Flow<List<CategoryEntity>> = flow {
        val response = RetrofitInstance.categoryApiService.getCategories()
        val categoryEntities = response.map { it.toEntity() }
        emit(categoryEntities)
    }

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> = flow {
        val response = RetrofitInstance.categoryApiService.getCategories()
        val category = response.find { it.id == id }?.toEntity()
        emit(category)
    }

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        categories.collect { list ->
            list.forEach { RetrofitInstance.categoryApiService.addCategory(it.toDto()) }
        }
    }

    override suspend fun delete(category: CategoryEntity) {
        RetrofitInstance.categoryApiService.deleteCategory(category.id)
    }

    override suspend fun update(category: CategoryEntity) {
        RetrofitInstance.categoryApiService.updateCategory(category.id, category.toDto())
    }
}