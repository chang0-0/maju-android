package com.app.majuapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.app.majuapp.util.SharedPreferencesUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        //Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                "walking_channel", "Walking Notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }
    } // End of onCreate()

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    }
} // End of Application class