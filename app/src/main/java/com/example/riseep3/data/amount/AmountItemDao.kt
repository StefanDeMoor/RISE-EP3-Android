package com.example.riseep3.data.amount

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AmountItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(amountItems: List<AmountItemEntity>)

    @Update
    suspend fun update(amountItem: AmountItemEntity)

    @Delete
    suspend fun delete(amountItem: AmountItemEntity)

    @Query("SELECT * FROM amount_items WHERE id = :id")
    fun getAmountItemById(id: Int): Flow<AmountItemEntity?>

    @Query("SELECT * FROM amount_items WHERE overviewId = :overviewId")
    fun getAmountsByOverviewId(overviewId: Int): Flow<List<AmountItemEntity>>

    @Query("DELETE FROM amount_items")
    suspend fun clearAll()
}
