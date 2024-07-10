package com.app.majuapp.di

import android.app.Application
import com.app.majuapp.data.repositoryImp.LocationTrackerImp
import com.app.majuapp.data.repositoryImp.LoginRepositoryImp
import com.app.majuapp.domain.api.LoginApi
import com.app.majuapp.domain.repository.LocationTracker
import com.app.majuapp.domain.repository.LoginRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideLoginRepository(loginApi: LoginApi) : LoginRepository = LoginRepositoryImp(loginApi)

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = LocationTrackerImp(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )
}