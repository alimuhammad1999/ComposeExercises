package com.example.exercises.Clima.Screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exercises.Clima.WeatherViewModel
import com.example.exercises.Clima.services.Location
import com.example.exercises.R
import kotlinx.coroutines.launch

@Composable
fun LocationScreen(
    viewModel: WeatherViewModel = viewModel(),
    onOpenCityScreen: () -> Unit // navigate to city input screen
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // collect UI state
    val temperature by viewModel.temperature.collectAsState()
    val weatherIcon by viewModel.weatherIcon.collectAsState()
    val message by viewModel.message.collectAsState()

    // Permission launcher for both fine & coarse
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        // perms: Map<String,Boolean>
        val fineGranted = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (fineGranted || coarseGranted) {
            // permission granted -> ask viewModel to fetch location-weather
            viewModel.fetchWeatherUsingDeviceLocation(context)
        } else {
            // user denied
            // update UI message or show toast
//            viewModelScopeToast(context, "Location permission denied")
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Helper UI function to show toast via viewModelScope launch
    // (We can't call Toast.show directly in preview; helper below.)
    // UI:
    Box(modifier = Modifier.fillMaxSize()) {
        // background image omitted here for brevity — add Image painterResource as earlier

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Near-me button
                IconButton(onClick = {
                    // check permission first
                    val fine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    val coarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

                    if (fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED) {
                        // already have permission
                        viewModel.fetchWeatherUsingDeviceLocation(context)
                    } else {
                        // request permission
                        permissionLauncher.launch(arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ))
                    }
                }) {
                    Icon(imageVector = Icons.Default.NearMe, contentDescription = "Near me", tint = Color.White, modifier = Modifier.size(50.dp))
                }

                // City button — navigate to city screen where user types name
                IconButton(onClick = { onOpenCityScreen() }) {
                    Icon(imageVector = Icons.Default.LocationCity, contentDescription = "City", tint = Color.White, modifier = Modifier.size(50.dp))
                }
            }

            // Temperature + icon row
            Row(modifier = Modifier.padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${temperature ?: "--"}°", style = MaterialTheme.typography.displayLarge, color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = weatherIcon ?: "?", style = MaterialTheme.typography.displayLarge)
            }

            // Message on bottom right
            Text(
                text = message ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}

// small helper to show toast from composable (optional)
@Composable
private fun viewModelScopeToast(context: Context, text: String) {
    LaunchedEffect(text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}

