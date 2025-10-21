package com.example.exercises.Clima.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exercises.Clima.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel
) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    // ⚡ Runs once when screen opens
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                weatherViewModel.getWeatherByLocation()
                // Navigate after successful fetch
                navController.navigate("location") {
                    popUpTo("loading") { inclusive = true } // removes loading from back stack
                }
            } catch (e: Exception) {
                e.printStackTrace()
                isLoading = false
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
