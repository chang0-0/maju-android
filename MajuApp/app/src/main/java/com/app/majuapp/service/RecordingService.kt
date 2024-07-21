package com.app.majuapp.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.majuapp.MainActivity
import com.app.majuapp.R
import com.app.majuapp.domain.model.walk.eventbus.EventBusEvent
import com.app.majuapp.domain.model.walk.eventbus.EventBusEvent.CurrentLocationEvent
import com.app.majuapp.screen.walk.WalkingEventBusViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.greenrobot.eventbus.EventBus


private const val TAG = "RecordingService_창영"

class RecordingService : Service() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var walkingEventBusViewModel: WalkingEventBusViewModel


    // Context
    private lateinit var context: Context

    // ServiceScope
    private val serviceScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        walkingEventBusViewModel = WalkingEventBusViewModel()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val temp = locationResult.locations[0]

                Log.d(TAG, "onLocationResult: ${temp.latitude} , ${temp.longitude}")
                EventBus.getDefault().post(
                    EventBusEvent.CurrentLocationEvent(
                        LatLng(
                            temp.latitude,
                            temp.longitude
                        )
                    )
                )
            }
        }
    } // End of onCreate()


    override fun onBind(p0: Intent?): IBinder? {
        return null
    } // End of onBind()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    } // End of onStartCommand()


    @SuppressLint("MissingPermission")
    private fun start() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val locationInterval = 2000L
        val locationFastestInterval = 500
        val locationMaxWaitTime = 500

        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, locationInterval)
                .setWaitForAccurateLocation(false)
                .build()

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )


        val notification = NotificationCompat.Builder(this, "walking_channel")
            .setSmallIcon(R.drawable.ic_home_logo).setContentTitle("산책중입니다!")
            .setContentIntent(pendingIntent).setContentText("Elapsed time").setOngoing(true).build()

        startForeground(1, notification)
    } // End of start()

    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }

    enum class Actions {
        START, STOP
    } // End of Actions class
} // End of RunningService class