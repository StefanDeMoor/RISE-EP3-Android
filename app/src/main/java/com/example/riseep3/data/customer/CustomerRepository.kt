package com.example.riseep3.data.customer

import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getAllCustomers(): Flow<List<CustomerEntity>>
    fun getCustomerById(id: Int): Flow<CustomerEntity>
    suspend fun insertAll(customers: Flow<List<CustomerEntity>>)
    suspend fun update(customer: CustomerEntity)
    suspend fun updateProfileImage(id: Int, path: String?)
    suspend fun delete(customer: CustomerEntity)
}