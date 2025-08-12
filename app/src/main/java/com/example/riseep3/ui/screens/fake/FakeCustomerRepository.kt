package com.example.riseep3.ui.screens.fake

import com.example.riseep3.data.customer.CustomerEntity
import com.example.riseep3.data.customer.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class FakeCustomerRepository : CustomerRepository {

    private val customersFlow = MutableStateFlow<List<CustomerEntity>>(emptyList())

    override fun getAllCustomers(): Flow<List<CustomerEntity>> = customersFlow

    override fun getCustomerById(id: Int): Flow<CustomerEntity> =
        MutableStateFlow(customersFlow.value.first { it.id == id })

    override suspend fun insertAll(customers: Flow<List<CustomerEntity>>) {
        customers.collect { list -> customersFlow.value = list }
    }

    override suspend fun update(customer: CustomerEntity) {
        val updatedList = customersFlow.value.map {
            if (it.id == customer.id) customer else it
        }
        customersFlow.value = updatedList
    }

    override suspend fun updateProfileImage(id: Int, path: String?) {
        val updatedList = customersFlow.value.map {
            if (it.id == id) it.copy(profileImagePath = path) else it
        }
        customersFlow.value = updatedList
    }

    override suspend fun delete(customer: CustomerEntity) {
        customersFlow.value = customersFlow.value.filter { it.id != customer.id }
    }
}

