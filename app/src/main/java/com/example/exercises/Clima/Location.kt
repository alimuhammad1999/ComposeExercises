package com.example.exercises.Clima

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class Location(private val context: Context) {

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    /**
     * Requests permission if needed and updates latitude & longitude.
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation() {
        try {
            val fine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            val coarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

            if (fine != PackageManager.PERMISSION_GRANTED && coarse != PackageManager.PERMISSION_GRANTED) {
                println("Location permission not granted")
                return
            }

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val location = fusedLocationClient.lastLocation.await()

            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                println("Latitude: $latitude, Longitude: $longitude")
            } else {
                println("Unable to fetch location (null response)")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
