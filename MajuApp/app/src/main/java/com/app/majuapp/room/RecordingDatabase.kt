package com.app.majuapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.majuapp.domain.model.walk.StepCountData


@Database(entities = [StepCountData::class], version = 1)
abstract class RecordingDatabase : RoomDatabase() {
    abstract fun recordingDao(): RecordingDao
} // End of RecordingDatabase class