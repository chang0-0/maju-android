package com.app.majuapp.room

import android.content.Context
import androidx.room.Room
import com.app.majuapp.util.Constants.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideImageDao(recordingDatabase: RecordingDatabase): RecordingDao =
        recordingDatabase.recordingDao()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RecordingDatabase {
        return Room.databaseBuilder(
            context,
            RecordingDatabase::class.java,
            DATABASE
        ).build()
    }
} // End of DatabaseModule
