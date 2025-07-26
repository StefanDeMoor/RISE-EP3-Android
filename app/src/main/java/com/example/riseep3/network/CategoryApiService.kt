package com.example.riseep3.network

import com.example.riseep3.domain.category.CategoryDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface CategoryApiService {
    @GET("api/categories")
    suspend fun getCategories(): List<CategoryDto>

    @POST("api/categories")
    suspend fun addCategory(@Body category: CategoryDto)

    @PUT("api/categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body category: CategoryDto)

    @DELETE("api/categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int)
}