package com.example.riseep3.data.category

import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository {
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> {
        return categoryDao.getCategory(id)
    }

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        categoryDao.insertAll(categories)
    }

    override suspend fun delete(category: CategoryEntity) {
        categoryDao.delete(category)
    }

    override suspend fun update(category: CategoryEntity) {
        categoryDao.update(category)
    }

}