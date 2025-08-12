package com.example.riseep3.data.customer

import android.util.Log
import com.example.riseep3.domain.customer.CustomerDto
import com.example.riseep3.domain.customer.toEntity
import com.example.riseep3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RemoteCustomerRepository(
    private val customerDao: CustomerDao
) : CustomerRepository {

    private val api = RetrofitInstance.customerApiService

    override fun getAllCustomers(): Flow<List<CustomerEntity>> = flow {
        val response = api.getCustomers()
        if (response.isSuccessful) {
            Log.d("RemoteCustomerRepo", "getCustomers succes: Response code = ${response.code()}")
            val customerDtos = response.body() ?: emptyList()
            val remoteCustomerEntities = customerDtos.map { it.toEntity() }

            val localCustomers = customerDao.getAllCustomers().first()

            val mergedCustomers = remoteCustomerEntities.map { remoteCustomer ->
                val localProfileImagePath = localCustomers.find { it.id == remoteCustomer.id }?.profileImagePath
                remoteCustomer.copy(profileImagePath = localProfileImagePath)
            }

            customerDao.insertAll(mergedCustomers)

            emit(mergedCustomers)
        } else {
            Log.d("RemoteCustomerRepo", "getCustomers failed: Response code = ${response.code()}")
            emit(emptyList())
        }
    }.catch { e ->
        Log.e("RemoteCustomerRepo", "Unexpected error", e)
        emit(emptyList())
    }


    override fun getCustomerById(id: Int): Flow<CustomerEntity> = customerDao.getCustomerById(id)

    override suspend fun insertAll(customers: Flow<List<CustomerEntity>>) {
        customers.collect { list ->
            list.forEach { customer ->
                val customerDto = CustomerDto(
                    id = customer.id,
                    firstName = customer.firstName,
                    lastName = customer.lastName,
                    email = customer.email,
                    phoneNumber = customer.phoneNumber,
                    profileImagePath = customer.profileImagePath
                )
                try {
                    val response = api.addCustomer(customerDto)
                    Log.d("RemoteCustomerRepo", "Insert success: Response code = ${response.code()}")
                } catch (e: Exception) {
                    Log.e("RemoteCustomerRepo", "Insert failed", e)
                }
            }
        }
    }

    override suspend fun update(customer: CustomerEntity) {
        val customerDto = CustomerDto(
            id = customer.id,
            firstName = customer.firstName,
            lastName = customer.lastName,
            email = customer.email,
            phoneNumber = customer.phoneNumber,
            profileImagePath = customer.profileImagePath
        )
        try {
            val response = api.updateCustomer(customer.id, customerDto)
            Log.d("RemoteCustomerRepo", "Update success: Response code = ${response.code()}")
        } catch (e: Exception) {
            Log.e("RemoteCustomerRepo", "Update failed", e)
        }
    }

    override suspend fun updateProfileImage(id: Int, path: String?) {
        try {
            val customer = customerDao.getCustomerById(id).first()
            val updatedCustomer = customer.copy(profileImagePath = path)

            val customerDto = CustomerDto(
                id = updatedCustomer.id,
                firstName = updatedCustomer.firstName,
                lastName = updatedCustomer.lastName,
                email = updatedCustomer.email,
                phoneNumber = updatedCustomer.phoneNumber,
                profileImagePath = updatedCustomer.profileImagePath
            )

            val response = api.updateCustomer(id, customerDto)
            if (response.isSuccessful) {
                Log.d("RemoteCustomerRepo", "Profile image updated via full update, code: ${response.code()}")
            } else {
                Log.e("RemoteCustomerRepo", "Failed to update profile image, code: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("RemoteCustomerRepo", "Exception during profile image update", e)
        }
    }


    override suspend fun delete(customer: CustomerEntity) {
        try {
            val response = api.deleteCustomer(customer.id)
            Log.d("RemoteCustomerRepo", "Delete success: Response code = ${response.code()}")
        } catch (e: Exception) {
            Log.e("RemoteCustomerRepo", "Delete failed", e)
        }
    }

}