package com.example.riseep3.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.data.AppDatabase
import com.example.riseep3.data.amount.AmountItemDao
import com.example.riseep3.data.amount.AmountItemEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AmountItemDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: AmountItemDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.amountItemDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndGetAllAmountItems() = runTest {
        val items = listOf(
            AmountItemEntity(id = 1, name = "Item1", amount = 100.0, overviewId = 1, parentAmountItemId = null),
            AmountItemEntity(id = 2, name = "Item2", amount = 200.0, overviewId = 1, parentAmountItemId = null)
        )

        dao.insertAll(items)
        val result = dao.getAllAmountItems().first()

        assertEquals(2, result.size)
        assertEquals("Item1", result[0].name)
        assertEquals("Item2", result[1].name)
    }

    @Test
    fun testGetSubAmounts() = runTest {
        val parent = AmountItemEntity(id = 1, name = "Parent", amount = 500.0, overviewId = 1, parentAmountItemId = null)
        val child1 = AmountItemEntity(id = 2, name = "Child1", amount = 50.0, overviewId = 1, parentAmountItemId = 1)
        val child2 = AmountItemEntity(id = 3, name = "Child2", amount = 75.0, overviewId = 1, parentAmountItemId = 1)

        dao.insertAll(listOf(parent, child1, child2))

        val result = dao.getSubAmounts(1)
        assertEquals(2, result.size)
        assertTrue(result.any { it.name == "Child1" })
        assertTrue(result.any { it.name == "Child2" })
    }

    @Test
    fun testUpdateAmountItem() = runTest {
        val item = AmountItemEntity(id = 1, name = "Original", amount = 300.0, overviewId = 1, parentAmountItemId = null)
        dao.insertAll(listOf(item))

        val updated = item.copy(name = "Updated", amount = 350.0)
        dao.update(updated)

        val result = dao.getAllAmountItems().first().first()
        assertEquals("Updated", result.name)
        assertEquals(350.0, result.amount, 0.01)
    }

    @Test
    fun testDeleteAmountItem() = runTest {
        val item = AmountItemEntity(id = 1, name = "ToDelete", amount = 150.0, overviewId = 1, parentAmountItemId = null)
        dao.insertAll(listOf(item))

        dao.delete(item)

        val result = dao.getAllAmountItems().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun testGetAllAmountItemsSortedByName() = runTest {
        val items = listOf(
            AmountItemEntity(id = 1, name = "Zebra", amount = 100.0, overviewId = 1, parentAmountItemId = null),
            AmountItemEntity(id = 2, name = "Apple", amount = 200.0, overviewId = 1, parentAmountItemId = null),
            AmountItemEntity(id = 3, name = "Mango", amount = 300.0, overviewId = 1, parentAmountItemId = null)
        )

        dao.insertAll(items)

        val result = dao.getAllAmountItems().first()
        val names = result.map { it.name }

        assertEquals(listOf("Apple", "Mango", "Zebra"), names)
    }
}
