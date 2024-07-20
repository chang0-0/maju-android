package com.app.majuapp.component.walk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.app.majuapp.domain.model.walk.CoordinateData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
private lateinit var locationCallback: LocationCallback


@SuppressLint("MissingPermission")
fun getLastUserLocation(
    context: Context,
    onGetLastLocationSuccess: (CoordinateData) -> Unit,
    onGetLastLocationFailed: (Exception) -> Unit,
    onGetLastLocationIsNull: () -> Unit
) {
    fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    // Check if location permissions are granted
    if (areLocationPermissionsGranted(context)) {
        // Retrieve the last known location
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with latitude and longitude
                    onGetLastLocationSuccess(CoordinateData(it.latitude, it.longitude))
                }?.run {
                    onGetLastLocationIsNull()
                }
            }
            .addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetLastLocationFailed(exception)
            }
    }


} // End of getLastUserLocation()


@SuppressLint("MissingPermission")
fun getCurrentLocation(
    context: Context,
    onGetCurrentLocationSuccess: (CoordinateData) -> Unit,
    onGetCurrentLocationFailed: (Exception) -> Unit,
    priority: Boolean = true,
    //walkViewModel: WalkViewModel = hiltViewModel()
) {
    val accuracy = if (priority) {
        Priority.PRIORITY_HIGH_ACCURACY
    } else {
        Priority.PRIORITY_BALANCED_POWER_ACCURACY
    }

    if (areLocationPermissionsGranted(context)) {
        fusedLocationProviderClient.getCurrentLocation(
            accuracy, CancellationTokenSource().token,
        ).addOnSuccessListener { location ->
            location?.let {
                onGetCurrentLocationSuccess(CoordinateData(it.latitude, it.longitude))
            }?.run {
                // Location null do something
            }
        }.addOnFailureListener { exception ->
            onGetCurrentLocationFailed(exception)
        }
    }
} // End of getCurrentLocation()

private fun areLocationPermissionsGranted(context: Context): Boolean {
    return (ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED)
} // End of areLocationPermissionsGranted()