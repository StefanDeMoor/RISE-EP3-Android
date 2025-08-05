package com.example.riseep3.viewmodels

import com.example.riseep3.data.amount.AmountItemEntity
import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.ui.screens.fake.FakeAmountItemRepository
import com.example.riseep3.ui.screens.fake.FakeOverviewRepository
import com.example.riseep3.ui.screens.overview.OverviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class OverviewViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeAmountItemRepository: FakeAmountItemRepository
    private lateinit var fakeOverviewRepository: FakeOverviewRepository
    private lateinit var viewModel: OverviewViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeAmountItemRepository = FakeAmountItemRepository().apply { reset() }
        fakeOverviewRepository = FakeOverviewRepository().apply { reset() }
        viewModel = OverviewViewModel(fakeAmountItemRepository, fakeOverviewRepository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadOverviewById_loadsCorrectData() = runTest {
        val overview = OverviewEntity(id = 1, title = "Test Overview", categoryId = 1, totalIncome = 100.0, result = 100.0)
        fakeOverviewRepository.insertAll(flowOf(listOf(overview)))

        val amountItem = AmountItemEntity(id = 1, name = "Adjustment1", amount = 50.0, overviewId = 1, parentAmountItemId = null)
        fakeAmountItemRepository.insertAll(flowOf(listOf(amountItem)))

        viewModel.loadOverviewById(1)
        advanceUntilIdle()

        val state = viewModel.uiState
        assertEquals(100.0, state.totalIncome, 0.001)
        assertTrue(state.isTotalIncomeSet)
        assertEquals("Test Overview", state.overviewTitle)
        assertEquals(1, state.adjustments.size)
        assertEquals("Adjustment1", state.adjustments[0].second)
    }


    @Test
    fun onIncomeChange_updatesTotalIncomeWhenNotSet() = runTest {
        viewModel.onIncomeChange(150.0)
        assertEquals(150.0, viewModel.uiState.totalIncome, 0.001)
    }

    @Test
    fun onIncomeConfirm_updatesRepositoryAndState() = runTest {
        val overview = OverviewEntity(id = 2, title = "Overview 2", categoryId = 1, totalIncome = null, result = 0.0)
        fakeOverviewRepository.insertAll(flowOf(listOf(overview)))

        viewModel.loadOverviewById(2)
        advanceUntilIdle()

        viewModel.onIncomeChange(200.0)
        viewModel.onIncomeConfirm()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.isTotalIncomeSet)
        assertEquals(200.0, viewModel.uiState.result, 0.001)

        val updatedOverview = fakeOverviewRepository.getOverviewById(2).first()
        assertEquals(200.0, updatedOverview?.totalIncome ?: 0.0, 0.001)
    }

    @Test
    fun onAdjustmentStart_and_onAmountConfirm_insertsNewAdjustment() = runTest {
        val overview = OverviewEntity(id = 3, title = "Overview 3", categoryId = 1, totalIncome = 0.0, result = 0.0)
        fakeOverviewRepository.insertAll(flowOf(listOf(overview)))
        viewModel.loadOverviewById(3)
        advanceUntilIdle()

        viewModel.onAdjustmentStart(isAddition = false)
        viewModel.onAmountNameChange("New Adjustment")
        viewModel.onAmountChange(123.45)
        viewModel.onAmountConfirm()
        advanceUntilIdle()

        val adjustments = viewModel.uiState.adjustments
        assertEquals(1, adjustments.size)
        assertEquals("New Adjustment", adjustments[0].second)
        assertEquals(123.45, adjustments[0].third, 0.001)
    }

    @Test
    fun onDeleteAdjustment_removesAdjustment() = runTest {
        val overview = OverviewEntity(id = 4, title = "Overview 4", categoryId = 1, totalIncome = 0.0, result = 0.0)
        fakeOverviewRepository.insertAll(flowOf(listOf(overview)))
        val item = AmountItemEntity(id = 10, name = "ToDelete", amount = 50.0, overviewId = 4, parentAmountItemId = null)
        fakeAmountItemRepository.insertAll(flowOf(listOf(item)))

        viewModel.loadOverviewById(4)
        advanceUntilIdle()

        assertEquals(1, viewModel.uiState.adjustments.size)

        viewModel.onDeleteAdjustment(0)
        advanceUntilIdle()

        assertEquals(0, viewModel.uiState.adjustments.size)
    }
}
