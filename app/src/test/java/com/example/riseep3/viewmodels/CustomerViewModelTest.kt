package com.example.riseep3.viewmodels

import com.example.riseep3.data.customer.CustomerEntity
import com.example.riseep3.ui.screens.customer.CustomerViewModel
import com.example.riseep3.ui.screens.fake.FakeCustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CustomerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakeCustomerRepository
    private lateinit var viewModel: CustomerViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeCustomerRepository()
        viewModel = CustomerViewModel(fakeRepository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadCustomers_loadsDataIntoState() = runTest {
        val customer = CustomerEntity(
            id = 1,
            firstName = "Alice",
            lastName = "Zephyr",
            profileImagePath = null,
            email = "alice@example.com",
            phoneNumber = "1234567890"
        )
        fakeRepository.insertAll(flowOf(listOf(customer)))

        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(1, state.customers.size)
        assertEquals("Alice", state.customers[0].firstName)
    }

    @Test
    fun updateProfileImage_updatesCustomerProfileImage() = runTest {
        val customer = CustomerEntity(
            id = 2,
            firstName = "Bob",
            lastName = "Yellow",
            profileImagePath = null,
            email = "bob@example.com",
            phoneNumber = "0987654321"
        )
        fakeRepository.insertAll(flowOf(listOf(customer)))
        advanceUntilIdle()

        viewModel.updateProfileImage(2, "/new/path/image.jpg")
        advanceUntilIdle()

        val state = viewModel.state.value
        val updatedCustomer = state.customers.firstOrNull { it.id == 2 }
        assertNotNull(updatedCustomer)
        assertEquals("/new/path/image.jpg", updatedCustomer?.profileImagePath)
    }

    @Test
    fun errorDuringLoad_setsErrorState() = runTest {
        val errorRepo = object : FakeCustomerRepository() {
            override fun getAllCustomers() = kotlinx.coroutines.flow.flow<List<CustomerEntity>> {
                throw RuntimeException("Load failure")
            }
        }

        val vm = CustomerViewModel(errorRepo)
        advanceUntilIdle()

        val state = vm.state.value
        assertFalse(state.isLoading)
        assertEquals("Load failure", state.error)
    }
}
