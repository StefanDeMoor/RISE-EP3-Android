package com.example.riseep3.repositories

import com.example.riseep3.data.amount.AmountItemEntity
import com.example.riseep3.data.amount.AmountItemRepository
import com.example.riseep3.ui.screens.fake.FakeAmountItemDao
import com.example.riseep3.data.amount.OfflineAmountItemRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class AmountItemRepositoryTest {

    @Test
    fun repository_returnsInsertedAmountItems(): Unit = runBlocking {
        val fakeDao = FakeAmountItemDao()
        val repo: AmountItemRepository = OfflineAmountItemRepository(fakeDao)

        val amountItem = AmountItemEntity(
            id = 1,
            name = "TestAmount",
            amount = 100.0,
            overviewId = 1,
            parentAmountItemId = null
        )

        repo.insertAll(flowOf(listOf(amountItem)))

        val all = repo.getAllAmountItem().first()
        assertTrue(all.any { it.name == "TestAmount" })
    }

    @Test
    fun repository_updatesAmountItem(): Unit = runBlocking {
        val fakeDao = FakeAmountItemDao()
        val repo: AmountItemRepository = OfflineAmountItemRepository(fakeDao)

        val original = AmountItemEntity(
            id = 1,
            name = "OldAmount",
            amount = 100.0,
            overviewId = 1,
            parentAmountItemId = null
        )

        fakeDao.insertAll(listOf(original))
        val updated = original.copy(name = "NewAmount", amount = 200.0)

        repo.update(updated)

        val result = repo.getAllAmountItem().first()
        assertTrue(result.any { it.name == "NewAmount" && it.amount == 200.0 })
    }

    @Test
    fun repository_deletesAmountItem(): Unit = runBlocking {
        val fakeDao = FakeAmountItemDao()
        val repo: AmountItemRepository = OfflineAmountItemRepository(fakeDao)

        val amountItem = AmountItemEntity(
            id = 1,
            name = "DeleteMe",
            amount = 50.0,
            overviewId = 1,
            parentAmountItemId = null
        )

        fakeDao.insertAll(listOf(amountItem))
        repo.delete(amountItem)

        val result = repo.getAllAmountItem().first()
        assertTrue(result.none { it.name == "DeleteMe" })
    }
}
