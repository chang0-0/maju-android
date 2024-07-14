package com.app.majuapp.di

import com.app.majuapp.domain.repository.CultureRepository
import com.app.majuapp.domain.repository.LoginRepository
import com.app.majuapp.domain.repository.ReissueRepository
import com.app.majuapp.domain.usecase.CultureUsecase
import com.app.majuapp.domain.usecase.LoginUsecase
import com.app.majuapp.domain.usecase.ReissueUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideLoginUsecase(loginRepository: LoginRepository): LoginUsecase = LoginUsecase(loginRepository)

    @Singleton
    @Provides
    fun provideCultureUsecase(cultureRepository: CultureRepository): CultureUsecase = CultureUsecase(cultureRepository)

    @Singleton
    @Provides
    fun provideReissueUsecase(reissueRepository: ReissueRepository): ReissueUsecase = ReissueUsecase(reissueRepository)

}