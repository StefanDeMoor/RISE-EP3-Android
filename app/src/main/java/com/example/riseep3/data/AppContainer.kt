package com.example.riseep3.data

import android.content.Context
import com.example.riseep3.data.amount.AmountItemRepository
import com.example.riseep3.data.amount.HybridAmountItemRepository
import com.example.riseep3.data.amount.OfflineAmountItemRepository
import com.example.riseep3.data.amount.RemoteAmountItemRepository
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
            remote = RemoteOverviewRepository(
                overviewDao = database.overviewDao(),
                amountItemDao = database.amountItemDao()
            ),
            local = OfflineOverviewRepository(database.overviewDao())
        )
    }
    val amountItemRepository: AmountItemRepository by lazy {
        HybridAmountItemRepository(
            remote = RemoteAmountItemRepository(database.amountItemDao()),
            local = OfflineAmountItemRepository(database.amountItemDao())
        )
    }

}
