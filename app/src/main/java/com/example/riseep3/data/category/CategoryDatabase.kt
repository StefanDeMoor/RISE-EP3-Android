package com.example.riseep3.data.category

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var Instance: CategoryDatabase? = null

        fun getDatabase(context: Context): CategoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CategoryDatabase::class.java, "category_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}