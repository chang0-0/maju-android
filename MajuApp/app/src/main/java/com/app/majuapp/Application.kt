package com.app.majuapp

import android.app.Application
import android.util.Log
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
        Log.d("accessToken", "${sharedPreferencesUtil.getUserAccessToken()}")
        Log.d("refreshToken", "${sharedPreferencesUtil.getUserRefreshToken()}")

    } // End of onCreate()

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    }
} // End of Application class