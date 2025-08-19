package com.example.riseep3.data.customer

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HybridCustomerRepository (
    private val remote: RemoteCustomerRepository,
    private val local: OfflineCustomerRepository
) : CustomerRepository {
    override fun getAllCustomers(): Flow<List<CustomerEntity>> = local.getAllCustomers().also {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteCustomers = remote.getAllCustomers().first()
                val localCustomers = local.getAllCustomers().first()

                val remoteIds = remoteCustomers.map { it.id }.toSet()
                val customersToDelete = localCustomers.filterNot { it.id in remoteIds }

                customersToDelete.forEach { local.delete(it) }
                local.insertAll(flowOf(remoteCustomers))
            } catch (e: Exception) {
                Log.e("HybridCustomerRepo", "Using cache", e)
            }
        }
    }

    override fun getCustomerById(id: Int): Flow<CustomerEntity> = local.getCustomerById(id)

    override suspend fun insert(customer: CustomerEntity) {
        remote.insert(customer)
        local.insert(customer)
    }

    override suspend fun insertAll(customers: Flow<List<CustomerEntity>>) {
        remote.insertAll(customers)
        local.insertAll(customers)
    }

    override suspend fun update(customer: CustomerEntity) {
        remote.update(customer)
        local.update(customer)
    }

    override suspend fun updateProfileImage(id: Int, path: String?) {
        remote.updateProfileImage(id, path)
        local.updateProfileImage(id, path)
    }

    override suspend fun delete(customer: CustomerEntity) {
        remote.delete(customer)
        local.delete(customer)
    }

}