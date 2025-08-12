package com.example.riseep3.repositories

import com.example.riseep3.data.customer.CustomerEntity
import com.example.riseep3.data.customer.CustomerRepository
import com.example.riseep3.ui.screens.fake.FakeCustomerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CustomerRepositoryTest {

    private val repo: CustomerRepository = FakeCustomerRepository()

    @Test
    fun repository_returnsInsertedCustomers(): Unit = runBlocking {
        val customer = CustomerEntity(
            id = 1,
            firstName = "Alice",
            lastName = "Zephyr",
            profileImagePath = null,
            email = "alice@example.com",
            phoneNumber = "1234567890"
        )
        repo.insertAll(flowOf(listOf(customer)))

        val all = repo.getAllCustomers().first()
        assertTrue(all.any { it.firstName == "Alice" })
    }

    @Test
    fun repository_returnsCustomerById(): Unit = runBlocking {
        val customer = CustomerEntity(
            id = 2,
            firstName = "Bob",
            lastName = "Yellow",
            profileImagePath = null,
            email = "bob@example.com",
            phoneNumber = "0987654321"
        )
        repo.insertAll(flowOf(listOf(customer)))

        val result = repo.getCustomerById(2).first()
        assertNotNull(result)
        assertEquals("Bob", result.firstName)
    }

    @Test
    fun repository_updatesCustomer(): Unit = runBlocking {
        val original = CustomerEntity(
            id = 3,
            firstName = "Charlie",
            lastName = "Xavier",
            profileImagePath = null,
            email = "charlie@example.com",
            phoneNumber = "5551234567"
        )
        repo.insertAll(flowOf(listOf(original)))

        val updated = original.copy(firstName = "Charlie Updated")
        repo.update(updated)

        val result = repo.getCustomerById(3).first()
        assertEquals("Charlie Updated", result.firstName)
    }

    @Test
    fun repository_updatesProfileImage(): Unit = runBlocking {
        val customer = CustomerEntity(
            id = 4,
            firstName = "Diana",
            lastName = "Winston",
            profileImagePath = null,
            email = "diana@example.com",
            phoneNumber = "4445556666"
        )
        repo.insertAll(flowOf(listOf(customer)))

        repo.updateProfileImage(4, "/images/profile4.jpg")

        val result = repo.getCustomerById(4).first()
        assertEquals("/images/profile4.jpg", result.profileImagePath)
    }

    @Test
    fun repository_deletesCustomer(): Unit = runBlocking {
        val customer = CustomerEntity(
            id = 5,
            firstName = "Evan",
            lastName = "Vance",
            profileImagePath = null,
            email = "evan@example.com",
            phoneNumber = "2223334444"
        )
        repo.insertAll(flowOf(listOf(customer)))

        repo.delete(customer)

        val all = repo.getAllCustomers().first()
        assertTrue(all.none { it.id == 5 })
    }
}