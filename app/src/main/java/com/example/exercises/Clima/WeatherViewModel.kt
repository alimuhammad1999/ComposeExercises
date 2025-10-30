package com.example.exercises.Clima

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercises.Clima.data.WeatherRepository
import com.example.exercises.Clima.data.model.WeatherData
import com.example.exercises.Clima.services.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


private val apiKey = "83f8e7f6ada557b6f32fd08f2354c919" // 🔒 Replace with your OpenWeather key

class WeatherViewModel : ViewModel() {
    private var _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData

    fun fetchWeatherByCity(context: Context, city: String) {
        val repo = WeatherRepository(context)
        viewModelScope.launch {
            _weatherData.value = repo.getWeatherByCity(city)
        }
    }

    fun fetchWeatherUsingDeviceLocation(context: Context) {
        val repo = WeatherRepository(context)
        viewModelScope.launch {
            _weatherData.value = repo.getWeatherByLocation()
        }
    }

    /*fun fetchWeatherUsingDeviceLocation(context: Context) {
        viewModelScope.launch {
            val locationService = LocationService(context)
            val coords = locationService.getCurrentLocation()

            if (coords != null) {
                // Now you have coords.latitude & coords.longitude
                getWeatherByLatLong(coords.latitude, coords.longitude)
            }
        }
    }*/

}