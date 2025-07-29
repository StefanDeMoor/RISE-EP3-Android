package com.example.riseep3.data.amount

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AmountItemDao {

    @Query("SELECT * FROM amount_items ORDER BY name ASC")
    fun getAllAmountItems(): Flow<List<AmountItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(amountItems: List<AmountItemEntity>)

    @Delete
    suspend fun delete(amountItem: AmountItemEntity)
}
