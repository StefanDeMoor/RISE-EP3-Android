package com.example.riseep3.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.riseep3.data.AppDatabase
import com.example.riseep3.data.customer.CustomerDao
import com.example.riseep3.data.customer.CustomerEntity
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
class CustomerDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: CustomerDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.customerDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndGetAllCustomers() = runTest {
        val customers = listOf(
            CustomerEntity(
                id = 1,
                firstName = "Alice",
                lastName = "Zephyr",
                profileImagePath = null,
                email = "alice@example.com",
                phoneNumber = "1234567890"
            ),
            CustomerEntity(
                id = 2,
                firstName = "Bob",
                lastName = "Yellow",
                profileImagePath = null,
                email = "bob@example.com",
                phoneNumber = "0987654321"
            )
        )
        dao.insertAll(customers)

        val result = dao.getAllCustomers().first()
        assertEquals(2, result.size)
        assertEquals("Alice", result[0].firstName)
        assertEquals("Zephyr", result[0].lastName)
        assertEquals("alice@example.com", result[0].email)
        assertEquals("1234567890", result[0].phoneNumber)

        assertEquals("Bob", result[1].firstName)
        assertEquals("Yellow", result[1].lastName)
        assertEquals("bob@example.com", result[1].email)
        assertEquals("0987654321", result[1].phoneNumber)
    }

    @Test
    fun testGetCustomerById() = runTest {
        val customer = CustomerEntity(
            id = 10,
            firstName = "Charlie",
            lastName = "Xavier",
            profileImagePath = null,
            email = "charlie@example.com",
            phoneNumber = "5551234567"
        )
        dao.insertAll(listOf(customer))

        val result = dao.getCustomerById(10).first()
        assertEquals("Charlie", result.firstName)
        assertEquals("Xavier", result.lastName)
        assertEquals("charlie@example.com", result.email)
        assertEquals("5551234567", result.phoneNumber)
    }

    @Test
    fun testUpdateCustomer() = runTest {
        val original = CustomerEntity(
            id = 5,
            firstName = "Diana",
            lastName = "Winston",
            profileImagePath = null,
            email = "diana@example.com",
            phoneNumber = "4445556666"
        )
        dao.insertAll(listOf(original))

        val updated = original.copy(
            firstName = "Diana Updated",
            lastName = "Winston Updated",
            email = "diana.updated@example.com",
            phoneNumber = "7778889999"
        )
        dao.update(updated)

        val result = dao.getCustomerById(5).first()
        assertEquals("Diana Updated", result.firstName)
        assertEquals("Winston Updated", result.lastName)
        assertEquals("diana.updated@example.com", result.email)
        assertEquals("7778889999", result.phoneNumber)
    }

    @Test
    fun testUpdateProfileImage() = runTest {
        val customer = CustomerEntity(
            id = 3,
            firstName = "Evan",
            lastName = "Vance",
            profileImagePath = null,
            email = "evan@example.com",
            phoneNumber = "2223334444"
        )
        dao.insertAll(listOf(customer))

        dao.updateProfileImage(3, "/new/path/image.jpg")

        val result = dao.getCustomerById(3).first()
        assertEquals("/new/path/image.jpg", result.profileImagePath)
    }

    @Test
    fun testDeleteCustomer() = runTest {
        val customer = CustomerEntity(
            id = 7,
            firstName = "Fiona",
            lastName = "Underwood",
            profileImagePath = null,
            email = "fiona@example.com",
            phoneNumber = "9998887777"
        )
        dao.insertAll(listOf(customer))

        dao.delete(customer)

        val result = dao.getAllCustomers().first()
        assertTrue(result.isEmpty())
    }
}
