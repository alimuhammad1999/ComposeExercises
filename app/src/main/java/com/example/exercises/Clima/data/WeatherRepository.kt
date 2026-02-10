package com.example.exercises.Clima.data


import android.content.Context
import com.example.exercises.Clima.data.model.WeatherData
import com.example.exercises.Clima.services.LocationService
import retrofit2.Response

class WeatherRepository(private val context: Context) {

    private val api = NetworkModule.retrofit.create(WeatherApi::class.java)
    private val apiKey = "83f8e7f6ada557b6f32fd08f2354c919"

    suspend fun getWeatherByCity(city: String): Response<WeatherData> {
        return api.getWeatherByCity(city, apiKey)
    }

    suspend fun getWeatherByLocation(): WeatherData? {
        val locationService = LocationService(context)
        val coords = locationService.getCurrentLocation() ?: return null
        return api.getWeatherByLocation(coords.latitude, coords.longitude, apiKey)
    }
}
