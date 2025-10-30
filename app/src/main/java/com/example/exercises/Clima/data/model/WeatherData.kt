package com.example.exercises.Clima.data.model

data class WeatherData(
    val name: String,
    val main: Main,
    val weather: List<WeatherDescription>
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
