package com.androidmvvmdatabindingrecyclerviewkotlin.network


import com.google.gson.JsonArray

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/users/{username}/repos")
    fun getRepos(@Path("username") username: String?): Call<JsonArray>

}
