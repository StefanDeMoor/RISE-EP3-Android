package com.example.riseep3.data.overview

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OverviewDao {
    @Query("SELECT * FROM overviews ORDER BY title ASC")
    fun getAllOverviews(): Flow<List<OverviewEntity>>

    @Query("SELECT * FROM overviews WHERE id = :id")
    fun getOverviewById(id: Int): Flow<OverviewEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(overviews: List<OverviewEntity>)

    @Update
    suspend fun update(overview: OverviewEntity)

    @Delete
    suspend fun delete(overview: OverviewEntity)
}