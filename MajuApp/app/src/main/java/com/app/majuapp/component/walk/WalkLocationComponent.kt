package com.app.majuapp.component.walk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import com.app.majuapp.domain.model.walk.CoordinateData
import com.app.majuapp.screen.walk.WalkViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource

private const val TAG = "WalkLocationComponent_창영"
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


@SuppressLint("MissingPermission")
fun getLastUserLocation(
    context: Context,
    onGetLastLocationSuccess: (CoordinateData) -> Unit,
    onGetLastLocationFailed: (Exception) -> Unit,
    onGetLastLocationIsNull: () -> Unit,
) {
//    var locationText by rememberSaveable { mutableStateOf("No location obtained :(") }
//    var showPermissionResultText by rememberSaveable { mutableStateOf(false) }
//    var permissionResultText by rememberSaveable { mutableStateOf("Permission Granted...") }


//    getLastUserLocation(context, onGetLastLocationSuccess = {
//        locationText = "Location using LAST-LOCATION: LATITUDE: ${it.lat}, LONGITUDE: ${it.lng}"
//    }, onGetLastLocationFailed = { exception ->
//        showPermissionResultText = true
//        locationText = exception.localizedMessage ?: "Error Getting Last Location"
//    }, onGetLastLocationIsNull = {
//        // Attempt to get the current user location
//        getCurrentLocation(context, onGetCurrentLocationSuccess = {
//            locationText =
//                "Location using CURRENT-LOCATION: LATITUDE: ${it.lat}, LONGITUDE: ${it.lng}"
//            walkViewModel.setCurrentLocation(LatLng(it.lat!!, it.lng!!))
//        }, onGetCurrentLocationFailed = {
//            showPermissionResultText = true
//            locationText = it.localizedMessage ?: "Error Getting Current Location"
//        })
//    })

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


@Composable
@SuppressLint("MissingPermission")
fun getCurrentLocation(
    context: Context,
    onGetCurrentLocationSuccess: (CoordinateData) -> Unit,
    onGetCurrentLocationFailed: (Exception) -> Unit,
    priority: Boolean = true,
    walkViewModel: WalkViewModel
) {
    val accuracy = if (priority) {
        Priority.PRIORITY_HIGH_ACCURACY
    } else {
        Priority.PRIORITY_BALANCED_POWER_ACCURACY
    }

    val result = remember { mutableStateOf<LatLng?>(null) }

    if (areLocationPermissionsGranted(context)) {
        fusedLocationProviderClient.getCurrentLocation(
            accuracy, CancellationTokenSource().token,
        ).addOnSuccessListener { location ->
            location?.let {
                onGetCurrentLocationSuccess(CoordinateData(it.latitude, it.longitude))
                // result.value = LatLng(it.latitude, it.longitude)
                walkViewModel.setCurrentLocation(LatLng(it.latitude, it.longitude))
            }?.run {
                // Location null do something
            }
        }.addOnFailureListener { exception ->
            onGetCurrentLocationFailed(exception)
        }
    }

    // return result.value
} // End of getCurrentLocation()

private fun areLocationPermissionsGranted(context: Context): Boolean {
    return (ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED)
} // End of areLocationPermissionsGranted()