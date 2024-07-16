package com.app.majuapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
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
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                "walking_channel",
                "walking_channel",
                NotificationManager.IMPORTANCE_HIGH // 소리 없이, 시각적 알림만
            )
            notificationManager.createNotificationChannel(channel)
        }
    } // End of onCreate()

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    }
} // End of Application class