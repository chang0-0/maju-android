package com.app.majuapp.component.walk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import com.app.majuapp.domain.model.walk.CoordinateData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit
) {
    // Initialize the state for managing multiple location permissions.
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )

    // Use LaunchedEffect to handle permissions logic when the composition is launched.
    LaunchedEffect(key1 = permissionState) {
        // Check if all previously granted permissions are revoked.
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        // Filter permissions that need to be requested.
        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        // If there are permissions to request, launch the permission request.
        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        // Execute callbacks based on permission status.
        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
} // End of RequestLocationPermission()

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestHealthPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit
) {
    val state = rememberMultiplePermissionsState(
        listOf(
            HealthPermission.getReadPermission(StepsRecord::class),
            HealthPermission.getWritePermission(StepsRecord::class)
        )
    )


    LaunchedEffect(key1 = state) {
        val allPermissionsRevoked = state.permissions.size == state.revokedPermissions.size

        val permissionsToRequest = state.permissions.filter {
            !it.status.isGranted
        }

        if (permissionsToRequest.isNotEmpty()) state.launchMultiplePermissionRequest()

        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (state.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}


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


// https://www.youtube.com/watch?v=f7YLwr4oUFE
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