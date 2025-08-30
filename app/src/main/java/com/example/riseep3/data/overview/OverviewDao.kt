package com.example.riseep3.data.overview

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OverviewDao {
    @Query("SELECT * FROM overviews ORDER BY title ASC")
    fun getAllOverviews(): Flow<List<OverviewEntity>>

    @Query("SELECT * FROM overviews WHERE id = :id")
    fun getOverviewById(id: Int): Flow<OverviewEntity?>

    @Query("SELECT * FROM overviews WHERE id = :id")
    suspend fun getOverviewByIdOnce(id: Int): OverviewEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(overview: OverviewEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(overviews: List<OverviewEntity>)

    @Update
    suspend fun update(overview: OverviewEntity)

    @Query("UPDATE overviews SET totalIncome = :newTotalIncome WHERE id = :id")
    suspend fun updateTotalIncome(id: Int, newTotalIncome: Double)

    @Delete
    suspend fun delete(overview: OverviewEntity)
}