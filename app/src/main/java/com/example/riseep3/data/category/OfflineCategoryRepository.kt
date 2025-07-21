package com.example.riseep3.data.category

import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val dao: CategoryDao) : CategoryRepository {
    override fun getAllCategories(): Flow<List<CategoryEntity>> = dao.getAllCategories()

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> = dao.getCategory(id)

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        categories.collect { dao.insertAll(it) }
    }

    override suspend fun delete(category: CategoryEntity) = dao.delete(category)

    override suspend fun update(category: CategoryEntity) = dao.update(category)
}