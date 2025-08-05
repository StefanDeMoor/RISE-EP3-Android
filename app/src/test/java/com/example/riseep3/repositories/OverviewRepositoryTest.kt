package com.example.riseep3.repositories

import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.ui.screens.fake.FakeOverviewRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class OverviewRepositoryTest {

    @Test
    fun repository_returnsInsertedOverviews(): Unit = runBlocking {
        val repo = FakeOverviewRepository()

        val overview = OverviewEntity(
            id = 1,
            title = "Test Overview",
            totalIncome = 1000.0,
            categoryId = 1,
            result = 0.0
        )
        repo.insertAll(flowOf(listOf(overview)))

        val all = repo.getAllOverviews().first()

        assertTrue(all.any { it.title == "Test Overview" })
    }

    @Test
    fun repository_returnsOverviewById(): Unit = runBlocking {
        val repo = FakeOverviewRepository()

        val overview = OverviewEntity(
            id = 2,
            title = "FindMe",
            totalIncome = 2000.0,
            categoryId = 1,
            result = 0.0
        )
        repo.insertAll(flowOf(listOf(overview)))

        val result = repo.getOverviewById(2).first()

        assertNotNull(result)
        assertEquals("FindMe", result?.title)
    }

    @Test
    fun repository_updatesOverview(): Unit = runBlocking {
        val repo = FakeOverviewRepository()

        val original = OverviewEntity(
            id = 3,
            title = "Old Title",
            totalIncome = 1500.0,
            categoryId = 1,
            result = 0.0
        )
        repo.insertAll(flowOf(listOf(original)))

        val updated = original.copy(title = "New Title")
        repo.update(updated)

        val result = repo.getOverviewById(3).first()

        assertEquals("New Title", result?.title)
    }

    @Test
    fun repository_updatesTotalIncome(): Unit = runBlocking {
        val repo = FakeOverviewRepository()

        val overview = OverviewEntity(
            id = 4,
            title = "Income Update",
            totalIncome = 500.0,
            categoryId = 1,
            result = 0.0
        )
        repo.insertAll(flowOf(listOf(overview)))

        repo.updateTotalIncome(4, 2500.0)

        val result = repo.getOverviewById(4).first()

        if (result != null) {
            result.totalIncome?.let { assertEquals(2500.0, it, 0.001) }
        }
    }

    @Test
    fun repository_deletesOverview(): Unit = runBlocking {
        val repo = FakeOverviewRepository()

        val overview = OverviewEntity(
            id = 5,
            title = "DeleteMe",
            totalIncome = 700.0,
            categoryId = 1,
            result = 0.0
        )
        repo.insertAll(flowOf(listOf(overview)))

        repo.delete(overview)

        val all = repo.getAllOverviews().first()

        assertTrue(all.none { it.title == "DeleteMe" })
    }
}
