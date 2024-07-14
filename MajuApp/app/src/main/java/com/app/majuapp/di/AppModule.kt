package com.app.majuapp.di

import com.app.majuapp.Application
import com.app.majuapp.BuildConfig
import com.app.majuapp.domain.api.CultureApi
import com.app.majuapp.domain.api.LoginApi
import com.app.majuapp.domain.api.ReissueApi
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
import javax.inject.Qualifier
import javax.inject.Singleton


private const val TAG = "AppModule_창영"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshHeaderInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithoutHeaderInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshInterceptorRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptorRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithoutHeaderInterceptorRetrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    class RefreshHeaderInterceptor @Inject constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", Application.sharedPreferencesUtil.getUserRefreshToken())
                .build()
            proceed(newRequest)
        }
    } // End of RefreshHeaderInterceptor class

    class HeaderInterceptor @Inject constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", Application.sharedPreferencesUtil.getUserAccessToken())
                .build()
            proceed(newRequest)
        }
    } // End of HeaderInterceptor class

    class AppInterceptor @Inject constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                // 여기서 토큰이나 API Key넣으면 됨
                .build()
            proceed(newRequest)
        }
    } // End of AppInterceptor class

    @RefreshHeaderInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesRefreshHeaderInterceptorOkHttpClient(appInterceptor: RefreshHeaderInterceptor): OkHttpClient {
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
    } // End of providesRefreshHeaderInterceptorOkHttpClient()

    @HeaderInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesHeaderInterceptorOkHttpClient(appInterceptor: HeaderInterceptor): OkHttpClient {
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
    } // End of providesHeaderInterceptorOkHttpClient()

    @WithoutHeaderInterceptorOkHttpClient
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
    fun providesApiService(@WithoutHeaderInterceptorOkHttpClient retrofit: Retrofit): TestApi {
        return retrofit.create(TestApi::class.java)
    } // End of providesApiService()

    @RefreshInterceptorRetrofit
    @Provides
    @Singleton
    fun providesRefreshHeaderInterceptorRetrofit(@RefreshHeaderInterceptorOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val rxAdapter = RxJava3CallAdapterFactory.create()

        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(rxAdapter)
            .client(okHttpClient)
            .build()
    } // End of providesRefreshHeaderInterceptorRetrofit()

    @HeaderInterceptorRetrofit
    @Provides
    @Singleton
    fun providesHeaderInterceptorRetrofit(@HeaderInterceptorOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val rxAdapter = RxJava3CallAdapterFactory.create()

        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(rxAdapter)
            .client(okHttpClient)
            .build()
    } // End of providesHeaderInterceptorRetrofit()

    @WithoutHeaderInterceptorRetrofit
    @Provides
    @Singleton
    fun providesRetrofit(@WithoutHeaderInterceptorOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val rxAdapter = RxJava3CallAdapterFactory.create()

        return Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(rxAdapter)
            .client(okHttpClient)
            .build()
    } // End of providesRetrofit()

    @Provides
    @Singleton
    fun providesLoginApi(@WithoutHeaderInterceptorRetrofit retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    } // End of providesLoginApi

    @Provides
    @Singleton
    fun providesCultureApi(@HeaderInterceptorRetrofit retrofit: Retrofit): CultureApi {
        return retrofit.create(CultureApi::class.java)
    } // End of providesCultureApi

    @Provides
    @Singleton
    fun providesReissueApi(@RefreshInterceptorRetrofit retrofit: Retrofit): ReissueApi {
        return retrofit.create(ReissueApi::class.java)
    }

} // End of AppModule class
