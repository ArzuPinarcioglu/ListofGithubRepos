package com.androidmvvmdatabindingrecyclerviewkotlin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "https://api.github.com/"
    private var retrofit: Retrofit? = null

    val service: ApiService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!.create(ApiService::class.java!!)
        }
}