package com.example.riseep3.network

import com.example.riseep3.domain.overview.OverviewDto
import retrofit2.Response
import retrofit2.http.*

interface OverviewApiService {
    @GET("api/overview")
    suspend fun getOverviews(): Response<List<OverviewDto>>

    @POST("api/overview")
    suspend fun addOverview(@Body overview: OverviewDto): Response<Unit>

    @PUT("api/overview/{id}")
    suspend fun updateOverview(@Path("id") id: Int, @Body overview: OverviewDto): Response<Unit>

    @PUT("api/overview/{id}/totalIncome")
    suspend fun updateTotalIncome(@Path("id") id: Int, @Body newTotalIncome: Double): Response<Unit>

    @DELETE("api/overview/{id}")
    suspend fun deleteOverview(@Path("id") id: Int): Response<Unit>
}
