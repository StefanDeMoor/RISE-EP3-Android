package com.example.riseep3.data.customer

import kotlinx.coroutines.flow.Flow

class OfflineCustomerRepository(private val dao: CustomerDao) : CustomerRepository {
    override fun getAllCustomers(): Flow<List<CustomerEntity>> = dao.getAllCustomers()

    override fun getCustomerById(id: Int): Flow<CustomerEntity> = dao.getCustomerById(id)

    override suspend fun insertAll(customers: Flow<List<CustomerEntity>>) {
        customers.collect { dao.insertAll(it) }
    }

    override suspend fun update(customer: CustomerEntity) = dao.update(customer)

    override suspend fun delete(customer: CustomerEntity) = dao.delete(customer)

}