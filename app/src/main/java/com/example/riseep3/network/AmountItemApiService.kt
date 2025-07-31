package com.example.riseep3.network

import com.example.riseep3.domain.amount.AmountItemDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AmountItemApiService {
    @GET("api/amount")
    suspend fun getAmountItems(): List<AmountItemDto>

    @POST("api/amount")
    suspend fun addAmountItem(@Body amountItem: AmountItemDto)
}