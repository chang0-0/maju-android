package com.app.majuapp.di

import com.app.majuapp.BuildConfig
import com.app.majuapp.domain.api.LoginApi
import com.app.majuapp.domain.api.TestApi
import com.app.majuapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "AppModule_창영"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    class AppInterceptor @Inject constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                // 여기서 토큰이나 API Key넣으면 됨
                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Client-ID ")
                .build()
            proceed(newRequest)
        }
    } // End of AppInterceptor class

    @Singleton
    @Provides
    fun providesOkHttpClient(appInterceptor: AppInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(appInterceptor)
            .build()
    } // End of providesOkHttpClient()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): TestApi {
        return retrofit.create(TestApi::class.java)
    } // End of providesApiService()

    @Provides
    @Singleton
    fun providesRetrofit(appInterceptor: AppInterceptor): Retrofit {
        val rxAdapter = RxJava3CallAdapterFactory.create()

        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(rxAdapter)
            .client(providesOkHttpClient(appInterceptor))
            .build()
    } // End of providesRetrofit()

    @Provides
    @Singleton
    fun providesLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

} // End of AppModule class
