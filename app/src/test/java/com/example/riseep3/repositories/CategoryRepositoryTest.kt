package com.example.riseep3.repositories

import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.category.OfflineCategoryRepository
import com.example.riseep3.ui.screens.fake.FakeCategoryDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class CategoryRepositoryTest {

    @Test
    fun repository_returnsInsertedCategories(): Unit = runBlocking {
        val fakeDao = FakeCategoryDao()
        val repo = OfflineCategoryRepository(fakeDao)

        val category = CategoryEntity(name = "TestCat")
        repo.insertAll(flowOf(listOf(category)))

        val all = repo.getAllCategories().first()

        assertTrue(all.any { it.name == "TestCat" })
    }
    @Test
    fun repository_returnsCategoryById(): Unit = runBlocking {
        val fakeDao = FakeCategoryDao()
        val repo = OfflineCategoryRepository(fakeDao)

        val category = CategoryEntity(name = "ByIdCat")
        fakeDao.insertAll(listOf(category))

        val inserted = fakeDao.getAllCategories().first().first()
        val result = repo.getCategoryById(inserted.id).first()

        assertTrue(result?.name == "ByIdCat")
    }

    @Test
    fun repository_updatesCategory(): Unit = runBlocking {
        val fakeDao = FakeCategoryDao()
        val repo = OfflineCategoryRepository(fakeDao)

        val original = CategoryEntity(name = "OldRepoName")
        fakeDao.insertAll(listOf(original))

        val inserted = fakeDao.getAllCategories().first().first()
        val updated = inserted.copy(name = "NewRepoName")

        repo.update(updated)

        val result = repo.getCategoryById(inserted.id).first()
        assertTrue(result?.name == "NewRepoName")
    }

    @Test
    fun repository_deletesCategory(): Unit = runBlocking {
        val fakeDao = FakeCategoryDao()
        val repo = OfflineCategoryRepository(fakeDao)

        val category = CategoryEntity(name = "DeleteRepoCat")
        fakeDao.insertAll(listOf(category))

        val inserted = fakeDao.getAllCategories().first().first()
        repo.delete(inserted)

        val result = repo.getAllCategories().first()
        assertTrue(result.none { it.name == "DeleteRepoCat" })
    }

}
