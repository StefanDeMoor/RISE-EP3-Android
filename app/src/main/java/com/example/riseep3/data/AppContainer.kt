package com.example.riseep3.data

import android.content.Context
import com.example.riseep3.data.category.CategoryRepository
import com.example.riseep3.data.category.HybridCategoryRepository
import com.example.riseep3.data.category.OfflineCategoryRepository
import com.example.riseep3.data.category.RemoteCategoryRepository
import com.example.riseep3.data.overview.HybridOverviewRepository
import com.example.riseep3.data.overview.OfflineOverviewRepository
import com.example.riseep3.data.overview.OverviewRepository
import com.example.riseep3.data.overview.RemoteOverviewRepository

class AppContainer(context: Context) {
    private val database = AppDatabase.getDatabase(context)

    val categoryRepository: CategoryRepository by lazy {
        HybridCategoryRepository(
            remote = RemoteCategoryRepository(),
            local = OfflineCategoryRepository(database.categoryDao())
        )
    }
    val overviewRepository: OverviewRepository by lazy {
        HybridOverviewRepository(
            remote = RemoteOverviewRepository(),
            local = OfflineOverviewRepository(database.overviewDao())
        )
    }


}
