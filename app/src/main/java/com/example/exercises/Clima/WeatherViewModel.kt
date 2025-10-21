package com.example.exercises.Clima

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercises.Clima.services.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class WeatherData(
    val main: Main,
    val weather: List<Weather>,
    val name: String
) {
    data class Main(val temp: Double)
    data class Weather(val id: Int)
}

private val apiKey = "83f8e7f6ada557b6f32fd08f2354c919" // 🔒 Replace with your OpenWeather key

class WeatherViewModel : ViewModel() {

    private val _temperature = MutableStateFlow<Int?>(null)
    val temperature: StateFlow<Int?> = _temperature.asStateFlow()

    private val _weatherIcon = MutableStateFlow<String?>(null)
    val weatherIcon: StateFlow<String?> = _weatherIcon.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    // NEW: call Location service and update UI state
    fun fetchWeatherUsingDeviceLocation(context: Context) {
        viewModelScope.launch {
            try {
                // 1) Get location
                val locationService = Location(context)
                locationService.getCurrentLocation() // suspend; updates latitude/longitude inside it

                val lat = locationService.latitude
                val lon = locationService.longitude

                if (lat == 0.0 && lon == 0.0) {
                    _temperature.value = null
                    _weatherIcon.value = "⚠️"
                    _message.value = "Could not get location"
                    return@launch
                }

                // 2) Fetch weather using existing suspend function
                val weather = getWeatherByLocation(lat, lon)
                if (weather == null) {
                    _temperature.value = null
                    _weatherIcon.value = "⚠️"
                    _message.value = "Error fetching weather"
                    return@launch
                }

                // 3) Update state flows
                val tempInt = weather.main.temp.toInt()
                _temperature.value = tempInt
                val condition = weather.weather.firstOrNull()?.id ?: 800
                _weatherIcon.value = getWeatherIcon(condition)
                _message.value = getMessage(tempInt) + " in ${weather.name}"

            } catch (e: Exception) {
                e.printStackTrace()
                _temperature.value = null
                _weatherIcon.value = "⚠️"
                _message.value = "Error: ${e.message}"
            }
        }
    }

    fun fetchWeatherByCityName(city: String) {
        viewModelScope.launch {
            val weather = getWeatherByCity(city)
            if (weather == null) {
                _message.value = "City not found"
                return@launch
            }
            val tempInt = weather.main.temp.toInt()
            _temperature.value = tempInt
            val condition = weather.weather.firstOrNull()?.id ?: 800
            _weatherIcon.value = getWeatherIcon(condition)
            _message.value = getMessage(tempInt) + " in ${weather.name}"
        }
    }

    fun getWeatherIcon(condition: Int): String {
        return when {
            condition < 300 -> "🌩"
            condition < 400 -> "🌧"
            condition < 600 -> "☔️"
            condition < 700 -> "☃️"
            condition < 800 -> "🌫"
            condition == 800 -> "☀️"
            condition <= 804 -> "☁️"
            else -> "🤷‍"
        }
    }

    fun getMessage(temp: Int): String {
        return when {
            temp > 25 -> "It's 🍦 time"
            temp > 20 -> "Time for shorts and 👕"
            temp < 10 -> "You'll need 🧣 and 🧤"
            else -> "Bring a 🧥 just in case"
        }
    }

    suspend fun getWeatherByLocation(lat: Double, lon: Double): WeatherData? {
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
        return fetchWeatherData(url)
    }

    suspend fun getWeatherByCity(name: String): WeatherData? {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$name&appid=$apiKey&units=metric"
        return fetchWeatherData(url)
    }

    private suspend fun fetchWeatherData(urlString: String): WeatherData? = withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStream = connection.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(inputStream)

            val main = json.getJSONObject("main")
            val weatherArray = json.getJSONArray("weather")
            val weatherObject = weatherArray.getJSONObject(0)

            WeatherData(
                main = WeatherData.Main(main.getDouble("temp")),
                weather = listOf(WeatherData.Weather(weatherObject.getInt("id"))),
                name = json.getString("name")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}