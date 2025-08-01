package com.example.riseep3.network

import com.example.riseep3.domain.category.CategoryDto
import retrofit2.Response
import retrofit2.http.*

interface CategoryApiService {

    @GET("api/categories")
    suspend fun getCategories(): Response<List<CategoryDto>>

    @POST("api/categories")
    suspend fun addCategory(@Body category: CategoryDto): Response<Unit>

    @PUT("api/categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body category: CategoryDto): Response<Unit>

    @DELETE("api/categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Response<Unit>
}
