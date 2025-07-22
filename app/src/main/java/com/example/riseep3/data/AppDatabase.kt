package com.example.riseep3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.riseep3.data.category.CategoryDao
import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.overview.OverviewDao
import com.example.riseep3.data.overview.OverviewEntity

@Database(
    entities = [CategoryEntity::class, OverviewEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun overviewDao(): OverviewDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration(false) // To prevent crash when schema changes in dev
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
