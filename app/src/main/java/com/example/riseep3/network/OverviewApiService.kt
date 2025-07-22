package com.example.riseep3.network

import com.example.riseep3.data.overview.OverviewEntity
import retrofit2.http.*

interface OverviewApiService {
    @GET("api/overviews")
    suspend fun getOverviews(): List<OverviewEntity>

    @POST("api/overviews")
    suspend fun addOverview(@Body overview: OverviewEntity)

    @PUT("api/overviews/{id}")
    suspend fun updateOverview(@Path("id") id: Int, @Body overview: OverviewEntity)

    @DELETE("api/overviews/{id}")
    suspend fun deleteOverview(@Path("id") id: Int)
}
