package com.example.exercises.Clima.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getData(@Url url: String): Map<String, Any>
}

object NetworkHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    suspend fun getData(fullUrl: String): Map<String, Any>? {
        return try {
            api.getData(fullUrl)
        } catch (e: Exception) {
            println("Request failed: ${e.message}")
            null
        }
    }
}
