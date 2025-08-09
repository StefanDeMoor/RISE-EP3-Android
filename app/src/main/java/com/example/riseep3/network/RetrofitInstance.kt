package com.example.riseep3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitInstance {

    private val unsafeOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5007")
            .client(unsafeOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val categoryApiService: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }

    val overviewApiService: OverviewApiService by lazy {
        retrofit.create(OverviewApiService::class.java)
    }

    val amountItemApiService: AmountItemApiService by lazy {
        retrofit.create(AmountItemApiService::class.java)
    }

    val customerApiService: CustomerApiService by lazy {
        retrofit.create(CustomerApiService::class.java)
    }
}
