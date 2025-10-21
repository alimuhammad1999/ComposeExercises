package com.example.exercises.Clima

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
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

class WeatherViewModel : ViewModel() {

    fun getWeatherByLocation() {

    }

    private val apiKey = "83f8e7f6ada557b6f32fd08f2354c919" // 🔒 Replace with your OpenWeather key

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