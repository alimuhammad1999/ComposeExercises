package com.example.exercises.Clima.Screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exercises.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exercises.Clima.WeatherViewModel
import java.util.Arrays



@Composable
fun LocationScreen(
    viewModel: WeatherViewModel = viewModel(),
    onOpenCityScreen: () -> Unit // navigate to City input screen
) {
    val context = LocalContext.current
    val weatherData by viewModel.weatherData.collectAsState()

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

    // 🔹 Ask for permission automatically when screen opens
//    LaunchedEffect(Unit) {
//        launcher.launch(
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        )
//    }

    // Background image similar to Flutter
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(id =  R.drawable.location_background),
            contentDescription = "Location Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.8f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

            // Top row with two buttons
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        launcher.launch(arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Get Weather by Location",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }

                IconButton(onClick = { onOpenCityScreen() }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Search City",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
            }

            // Weather Data Section
            if (weatherData != null) {
                val temp = weatherData!!.main.temp.toInt()
                val city = weatherData!!.name
                val conditionid = weatherData!!.weather.firstOrNull()?.id ?: 0 //viewModel.getWeatherIcon(temp)
                val condition = viewModel.getWeatherIcon(conditionid.toInt())
                val message = viewModel.getMessage(temp)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$temp°",
                            fontSize = 80.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = condition,
                            fontSize = 40.sp,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "$message in $city",
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                // Loading or empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}



/*
@Composable
fun LocationScreen(
    viewModel: WeatherViewModel = viewModel(),
    onOpenCityScreen: () -> Unit // navigate to city input screen
) {
    val context = LocalContext.current
    val locationService = remember { LocationService(context) }

    // Permission launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Fetch location once granted
            viewModel.fetchWeatherUsingDeviceLocation(context)
        } else {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Ask for permission on button click
    Button(onClick = {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    },
        colors = ButtonColors(
            containerColor = Color.Red,
            contentColor = Color.White,
            disabledContainerColor = Color.Blue,
            disabledContentColor = Color.White
        )) {
        Text("Get Weather by Location", color = Color.White)
    }
}*/
