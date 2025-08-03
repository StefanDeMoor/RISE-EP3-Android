package com.example.riseep3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.riseep3.data.amount.AmountItemDao
import com.example.riseep3.data.amount.AmountItemEntity
import com.example.riseep3.data.category.CategoryDao
import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.overview.OverviewDao
import com.example.riseep3.data.overview.OverviewEntity

@Database(
    entities = [CategoryEntity::class, OverviewEntity::class, AmountItemEntity::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun overviewDao(): OverviewDao
    abstract fun amountItemDao(): AmountItemDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
