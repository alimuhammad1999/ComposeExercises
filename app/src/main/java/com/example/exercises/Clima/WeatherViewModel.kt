package com.example.exercises.Clima

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercises.Clima.data.WeatherRepository
import com.example.exercises.Clima.data.model.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class WeatherViewModel : ViewModel() {
    private var _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun fetchWeatherByCity(context: Context, city: String) {
        val repo = WeatherRepository(context)
        viewModelScope.launch {
            _weatherData.value = repo.getWeatherByCity(city)
        }
    }

    /*fun fetchWeatherUsingDeviceLocation(context: Context) {
        val repo = WeatherRepository(context)
        viewModelScope.launch {
            _weatherData.value = repo.getWeatherByLocation()
        }
    }*/

    fun fetchWeatherUsingDeviceLocation(context: Context) {
        val repo = WeatherRepository(context)
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val data = repo.getWeatherByLocation()
                Log.d("WeatherViewModel", "Fetched weather data: $data")
                if (data != null) {
                    _weatherData.value = data
                } else {
                    _error.value = "Failed to fetch weather"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getWeatherIcon(condition: Int): String {
        if (condition < 300) {
            return "🌩"
        } else if (condition < 400) {
            return "🌧"
        } else if (condition < 600) {
            return "☔️"
        } else if (condition < 700) {
            return "☃️"
        } else if (condition < 800) {
            return "🌫"
        } else if (condition == 800) {
            return "☀️"
        } else if (condition <= 804) {
            return "☁️"
        } else {
            return "🤷‍"
        }
    }

    fun getMessage(temp: Int): String {
        if (temp > 25) {
            return "It\'s 🍦 time"
        } else if (temp > 20) {
            return "Time for shorts and 👕"
        } else if (temp < 10) {
            return "You\'ll need 🧣 and 🧤"
        } else {
            return "Bring a 🧥 just in case"
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