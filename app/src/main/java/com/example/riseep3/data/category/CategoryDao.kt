package com.example.riseep3.data.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(categories: Flow<List<CategoryEntity>>)

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Query("SELECT * from categories WHERE id = :id")
    fun getCategory(id: Int): Flow<CategoryEntity>

    @Query("SELECT * from categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}