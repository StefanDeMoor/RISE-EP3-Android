package com.example.riseep3.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.data.AppDatabase
import com.example.riseep3.data.category.CategoryDao
import com.example.riseep3.data.category.CategoryEntity
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
class CategoryDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: CategoryDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.categoryDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndGetAllCategories() = runTest {
        val categories = listOf(
            CategoryEntity(name = "Cat1"),
            CategoryEntity(name = "Cat2")
        )
        dao.insertAll(categories)

        val emitted = dao.getAllCategories().first()

        assertEquals(2, emitted.size)
        assertEquals("Cat1", emitted[0].name)
        assertEquals("Cat2", emitted[1].name)
    }

    @Test
    fun testGetCategoryById() = runTest {
        val category = CategoryEntity(name = "GetById")
        dao.insertAll(listOf(category))

        val inserted = dao.getAllCategories().first().first()
        val result = dao.getCategory(inserted.id).first()

        assertEquals("GetById", result.name)
    }

    @Test
    fun testUpdateCategory() = runTest {
        val original = CategoryEntity(name = "OldName")
        dao.insertAll(listOf(original))

        val inserted = dao.getAllCategories().first().first()
        val updated = inserted.copy(name = "NewName")
        dao.update(updated)

        val result = dao.getCategory(inserted.id).first()
        assertEquals("NewName", result.name)
    }

    @Test
    fun testDeleteCategory() = runTest {
        val category = CategoryEntity(name = "ToDelete")
        dao.insertAll(listOf(category))

        val inserted = dao.getAllCategories().first().first()
        dao.delete(inserted)

        val result = dao.getAllCategories().first()
        assertEquals(0, result.size)
    }

    @Test
    fun testGetAllCategoriesSortedByName() = runTest {
        val categories = listOf(
            CategoryEntity(name = "Zoo"),
            CategoryEntity(name = "Apple"),
            CategoryEntity(name = "Mango")
        )
        dao.insertAll(categories)

        val result = dao.getAllCategories().first()

        val sortedNames = result.map { it.name }
        assertEquals(listOf("Apple", "Mango", "Zoo"), sortedNames)
    }


}
