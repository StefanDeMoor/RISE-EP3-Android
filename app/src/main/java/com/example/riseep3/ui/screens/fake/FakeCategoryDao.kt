package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.category.CategoryDao
import com.example.riseep3.data.category.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeCategoryDao : CategoryDao {
    private val categoriesFlow = MutableStateFlow<List<CategoryEntity>>(emptyList())

    override suspend fun insertAll(categories: List<CategoryEntity>) {
        categoriesFlow.update { current ->
            current + categories
        }
    }

    override suspend fun update(category: CategoryEntity) {
        categoriesFlow.update { current ->
            current.map { if (it.id == category.id) category else it }
        }
    }

    override suspend fun delete(category: CategoryEntity) {
        categoriesFlow.update { current ->
            current.filter { it.id != category.id }
        }
    }

    override fun getCategory(id: Int): Flow<CategoryEntity> {
        return categoriesFlow.map { list -> list.firstOrNull { it.id == id } ?: CategoryEntity(0, "") }
    }

    override fun getAllCategories(): Flow<List<CategoryEntity>> = categoriesFlow
}
