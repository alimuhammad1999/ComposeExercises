package com.example.exercises.Clima.Screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exercises.Clima.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(
    navController: NavController,
    viewModel: WeatherViewModel
) {
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val weatherData by viewModel.weatherData.collectAsState()
    val error by viewModel.error.collectAsState()

    // Permission launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val coarse = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        val fine = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val isGranted = coarse || fine

        if (isGranted) viewModel.fetchWeatherUsingDeviceLocation(context)
        else Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()

    }

    // ⚡ Runs once when screen opens
    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    // 🚀 Observe data and navigate once it's available
    LaunchedEffect(weatherData) {
        if (weatherData != null) {
            navController.navigate(WeatherAppScreens.Location.name) {
                popUpTo("Loading") {
                    inclusive = true
                }
            }
        }
    }

    // 🌀 Loading UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0E21)),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Fetching Weather...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Text(
                text = "Error fetching location!",
                color = Color.Red,
                fontSize = 18.sp
            )
        }
    }
}
