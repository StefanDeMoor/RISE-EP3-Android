package com.example.riseep3.data.amount

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AmountItemDao {

    @Query("SELECT * FROM amount_items ORDER BY name ASC")
    fun getAllAmountItems(): Flow<List<AmountItemEntity>>

    @Query("SELECT * FROM amount_items WHERE parentAmountItemId = :parentId")
    suspend fun getSubAmounts(parentId: Int): List<AmountItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(amountItems: List<AmountItemEntity>)

    @Update
    suspend fun update(amountItem: AmountItemEntity)

    @Delete
    suspend fun delete(amountItem: AmountItemEntity)
}
