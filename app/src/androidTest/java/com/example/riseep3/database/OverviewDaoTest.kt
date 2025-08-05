package com.example.riseep3.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.data.AppDatabase
import com.example.riseep3.data.category.CategoryDao
import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.overview.OverviewDao
import com.example.riseep3.data.overview.OverviewEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class OverviewDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: OverviewDao
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.overviewDao()
        categoryDao = db.categoryDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndGetAllOverviews() = runTest {
        categoryDao.insertAll(listOf(CategoryEntity(id = 1, name = "TestCategory")))

        val overviews = listOf(
            OverviewEntity(id = 1, title = "One", categoryId = 1, totalIncome = 100.0, result = 10.0),
            OverviewEntity(id = 2, title = "Two", categoryId = 1, totalIncome = 200.0, result = 20.0)
        )
        dao.insertAll(overviews)

        val result = dao.getAllOverviews().first()

        assertEquals(2, result.size)
        assertEquals("One", result[0].title)
        assertEquals("Two", result[1].title)
    }

    @Test
    fun testGetOverviewById() = runTest {
        categoryDao.insertAll(listOf(CategoryEntity(id = 1, name = "TestCategory")))
        val overview = OverviewEntity(id = 10, title = "ById", categoryId = 1, totalIncome = 50.0, result = 5.0)
        dao.insertAll(listOf(overview))

        val result = dao.getOverviewById(10).first()
        assertEquals("ById", result?.title)
    }

    @Test
    fun testUpdateOverview() = runTest {
        categoryDao.insertAll(listOf(CategoryEntity(id = 1, name = "TestCategory")))
        val original = OverviewEntity(id = 5, title = "OldTitle", categoryId = 1, totalIncome = 0.0, result = 0.0)
        dao.insertAll(listOf(original))

        val updated = original.copy(title = "NewTitle", totalIncome = 123.45)
        dao.update(updated)

        val result = dao.getOverviewById(5).first()
        assertEquals("NewTitle", result?.title)
        assertEquals(123.45, result?.totalIncome ?: 0.0, 0.001)
    }

    @Test
    fun testUpdateTotalIncome() = runTest {
        categoryDao.insertAll(listOf(CategoryEntity(id = 1, name = "TestCategory")))
        val overview = OverviewEntity(id = 3, title = "IncomeTest", categoryId = 1, totalIncome = 10.0, result = 0.0)
        dao.insertAll(listOf(overview))

        dao.updateTotalIncome(3, 99.99)

        val result = dao.getOverviewByIdOnce(3)
        assertEquals(99.99, result?.totalIncome ?: 0.0, 0.001)
    }

    @Test
    fun testDeleteOverview() = runTest {
        categoryDao.insertAll(listOf(CategoryEntity(id = 1, name = "TestCategory")))
        val overview = OverviewEntity(id = 7, title = "ToDelete", categoryId = 1, totalIncome = 0.0, result = 0.0)
        dao.insertAll(listOf(overview))

        dao.delete(overview)

        val result = dao.getAllOverviews().first()
        assertEquals(0, result.size)
    }

    @Test
    fun testGetAllOverviewsSortedByTitle() = runTest {
        categoryDao.insertAll(listOf(CategoryEntity(id = 1, name = "TestCategory")))

        val overviews = listOf(
            OverviewEntity(id = 1, title = "Zoo", categoryId = 1, totalIncome = 0.0, result = 0.0),
            OverviewEntity(id = 2, title = "Apple", categoryId = 1, totalIncome = 0.0, result = 0.0),
            OverviewEntity(id = 3, title = "Mango", categoryId = 1, totalIncome = 0.0, result = 0.0)
        )
        dao.insertAll(overviews)

        val result = dao.getAllOverviews().first()
        val titles = result.map { it.title }

        assertEquals(listOf("Apple", "Mango", "Zoo"), titles)
    }
}
