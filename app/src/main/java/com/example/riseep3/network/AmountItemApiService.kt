package com.example.riseep3.network

import com.example.riseep3.domain.amount.AmountItemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AmountItemApiService {
    @GET("api/amount")
    suspend fun getAmountItems(): List<AmountItemDto>

    @POST("api/amount")
    suspend fun addAmountItem(@Body amountItem: AmountItemDto)

    @DELETE("api/amount/{id}")
    suspend fun deleteAmountItem(@Path("id") id: Int): Response<Unit>
}