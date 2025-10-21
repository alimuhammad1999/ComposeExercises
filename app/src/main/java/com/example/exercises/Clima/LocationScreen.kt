package com.example.exercises.Clima

package com.example.clima.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exercises.R
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun LocationScreen(
    weather: WeatherData? = null,
    navigateToCityScreen: suspend () -> WeatherData? // this is your CityScreen navigation callback
) {
    val viewModel: WeatherViewModel = viewModel()
    val scope = rememberCoroutineScope()

    var temperature by remember { mutableStateOf<Int?>(null) }
    var weatherIcon by remember { mutableStateOf("❓") }
    var message by remember { mutableStateOf("Loading weather...") }

    // --- Updates UI when new data arrives ---
    fun updateData(weatherData: WeatherData?) {
        if (weatherData == null) {
            temperature = 0
            weatherIcon = "⚠️"
            message = "Error fetching data"
            return
        }

        val temp = weatherData.main.temp.toInt()
        val condition = weatherData.weather.firstOrNull()?.id ?: 800
        val cityName = weatherData.name

        temperature = temp
        weatherIcon = viewModel.getWeatherIcon(condition)
        message = viewModel.getMessage(temp) + " in $cityName"
    }

    // --- Initialize once with weather from previous screen (if passed) ---
    LaunchedEffect(Unit) {
        updateData(weather)
    }

    // --- UI starts here ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0E21))
    ) {
        Image(
            painter = painterResource(id = R.drawable.location_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.8f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            // --- Top Row Buttons ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    scope.launch {
                        val weatherData = viewModel.getWeatherByLocation(24.8607, 67.0011) // Karachi for now
                        updateData(weatherData)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_near_me),
                        contentDescription = "Near Me",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }

                IconButton(onClick = {
                    scope.launch {
                        val cityWeather = navigateToCityScreen()
                        if (cityWeather != null) updateData(cityWeather)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location_city),
                        contentDescription = "City",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
            }

            // --- Weather Info ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Text(
                    text = "${temperature ?: "--"}°",
                    color = Color.White,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weatherIcon,
                    fontSize = 64.sp
                )
            }

            // --- Bottom Message ---
            Text(
                text = message,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            )
        }
    }
}

