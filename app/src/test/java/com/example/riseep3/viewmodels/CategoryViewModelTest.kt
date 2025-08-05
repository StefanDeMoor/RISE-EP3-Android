package com.example.riseep3.viewmodels

import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.category.OfflineCategoryRepository
import com.example.riseep3.ui.screens.category.CategoryViewModel
import com.example.riseep3.ui.screens.fake.FakeCategoryDao
import com.example.riseep3.ui.screens.fake.FakeOverviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun viewModel_loadsCategories(): Unit = runTest {
        val fakeDao = FakeCategoryDao()
        val fakeRepo = OfflineCategoryRepository(fakeDao)
        val fakeOverviewRepo = FakeOverviewRepository()

        fakeDao.insertAll(listOf(CategoryEntity(name = "CatInVM")))

        val vm = CategoryViewModel(fakeRepo, fakeOverviewRepo)

        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state.categories.any { it.name == "CatInVM" })
    }

    @Test
    fun viewModel_selectCategory_updatesSelectedCategory(): Unit = runTest {
        val vm = createTestViewModel()

        vm.selectCategory("MyCategory")
        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state.selectedCategory == "MyCategory")
    }

    @Test
    fun viewModel_addCreatedItem_addsItemCorrectly(): Unit = runTest {
        val vm = createTestViewModel()

        vm.addCreatedItem("NewItem", "NewCategory")
        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state.createdItems.contains("NewItem" to "NewCategory"))
    }

    @Test
    fun viewModel_updateAndClearNewItemName_worksCorrectly(): Unit = runTest {
        val vm = createTestViewModel()

        vm.updateNewItemName("SomeName")
        assertTrue(vm.uiState.value.newItemName == "SomeName")

        vm.clearNewItemName()
        assertTrue(vm.uiState.value.newItemName.isEmpty())
    }

    @Test
    fun viewModel_setShowNewItemDialog_setsFlag(): Unit = runTest {
        val vm = createTestViewModel()

        vm.setShowNewItemDialog(true)
        assertTrue(vm.uiState.value.isDialogOpen)

        vm.setShowNewItemDialog(false)
        assertTrue(!vm.uiState.value.isDialogOpen)
    }

    @Test
    fun viewModel_setShowSuccessDialog_setsFlag(): Unit = runTest {
        val vm = createTestViewModel()

        vm.setShowSuccessDialog(true)
        assertTrue(vm.uiState.value.showSuccessDialog)

        vm.setShowSuccessDialog(false)
        assertTrue(!vm.uiState.value.showSuccessDialog)
    }

}

private fun createTestViewModel(): CategoryViewModel {
    val fakeDao = FakeCategoryDao()
    val fakeRepo = OfflineCategoryRepository(fakeDao)
    val fakeOverviewRepo = FakeOverviewRepository()
    return CategoryViewModel(fakeRepo, fakeOverviewRepo)
}


