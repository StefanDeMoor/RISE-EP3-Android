package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.category.CategoryRepository
import kotlinx.coroutines.flow.Flow

class FakeCategoryRepository : CategoryRepository {
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: Int): Flow<CategoryEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(categories: Flow<List<CategoryEntity>>) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(category: CategoryEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun update(category: CategoryEntity) {
        TODO("Not yet implemented")
    }

}