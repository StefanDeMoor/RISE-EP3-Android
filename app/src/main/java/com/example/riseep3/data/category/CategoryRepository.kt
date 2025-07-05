package com.example.riseep3.data.category

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryEntity>>
    fun getCategoryById(id: Int): Flow<CategoryEntity?>
    suspend fun insertAll(categories: Flow<List<CategoryEntity>>)
    suspend fun delete(category: CategoryEntity)
    suspend fun update(category: CategoryEntity)
}