package com.example.riseep3.network

import com.example.riseep3.domain.amount.AmountItemDto
import retrofit2.http.GET

interface AmountItemApiService {
    @GET("api/amount")
    suspend fun getAmountItems(): List<AmountItemDto>
}