package com.example.riseep3.network

import com.example.riseep3.domain.overview.OverviewDto
import com.example.riseep3.domain.overview.OverviewRequestWrapper
import retrofit2.http.*

interface OverviewApiService {
    @GET("api/overview")
    suspend fun getOverviews(): List<OverviewDto>

    @POST("api/overview")
    suspend fun addOverview(@Body overview: OverviewDto)

    @PUT("api/overview/{id}")
    suspend fun updateOverview(@Path("id") id: Int, @Body overviewWrapper: OverviewRequestWrapper)

    @PUT("api/overview/{id}/totalincome")
    suspend fun updateTotalIncome(@Path("id") id: Int, @Body newTotalIncome: Double)

    @DELETE("api/overview/{id}")
    suspend fun deleteOverview(@Path("id") id: Int)
}
