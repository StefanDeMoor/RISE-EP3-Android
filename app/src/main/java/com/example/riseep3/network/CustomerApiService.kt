package com.example.riseep3.network

import com.example.riseep3.domain.customer.CustomerDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApiService {
    @GET("api/customer")
    suspend fun getCustomers(): Response<List<CustomerDto>>

    @POST("api/customer")
    suspend fun addCustomer(@Body customer: CustomerDto): Response<Unit>

    @PUT("api/customer/{id}")
    suspend fun updateCustomer(@Path("id") id: Int, @Body customer: CustomerDto): Response<Unit>

    @DELETE("api/customer/{id}")
    suspend fun deleteCustomer(@Path("id") id: Int): Response<Unit>
}