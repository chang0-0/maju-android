package com.app.majuapp.di

import com.app.majuapp.data.repositoryImp.LoginRepositoryImp
import com.app.majuapp.domain.api.LoginApi
import com.app.majuapp.domain.repository.LoginRepository
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

}