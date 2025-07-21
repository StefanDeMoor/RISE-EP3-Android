package com.example.riseep3.data

import android.content.Context
import com.example.riseep3.data.category.CategoryDatabase
import com.example.riseep3.data.category.CategoryRepository
import com.example.riseep3.data.category.HybridCategoryRepository
import com.example.riseep3.data.category.OfflineCategoryRepository
import com.example.riseep3.data.category.RemoteCategoryRepository

class AppContainer(context: Context) {
    private val database = CategoryDatabase.getDatabase(context)

    val categoryRepository: CategoryRepository by lazy {
        HybridCategoryRepository(
            remote = RemoteCategoryRepository(),
            local = OfflineCategoryRepository(database.categoryDao())
        )
    }
}
