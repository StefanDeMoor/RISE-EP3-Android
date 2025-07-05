package com.example.riseep3.data

import android.content.Context
import com.example.riseep3.data.category.CategoryDatabase
import com.example.riseep3.data.category.CategoryRepository
import com.example.riseep3.data.category.OfflineCategoryRepository

class AppContainer(context: Context) {
    val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(
            CategoryDatabase.getDatabase(context).categoryDao()
        )
    }
}