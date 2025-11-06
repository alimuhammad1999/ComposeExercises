package com.example.exercises.Clima.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

class LocationService(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Requests location permission if not granted and returns current coordinates.
     * Returns null if permission not granted.
     */
    suspend fun getCurrentLocation(): Coordinates? {
        // Check permission
        val fine_permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarse_permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION)


        if (fine_permission != PackageManager.PERMISSION_GRANTED &&
            coarse_permission != PackageManager.PERMISSION_GRANTED
        ) {
//             Permission not granted → return null (you’ll request it from UI)
            return null
        }

        // Fetch last known location
        val location = fusedLocationClient.lastLocation.await()
        return if (location != null) {
            Coordinates(location.latitude, location.longitude)
        } else null
    }
}