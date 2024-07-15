package com.app.majuapp.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordingRepository @Inject constructor(
    private val recordingDatabase: RecordingDatabase
) {

    fun getStepCountById(id: Int) =
        recordingDatabase.recordingDao().getStepCountById(id).flowOn(Dispatchers.IO).conflate()

    suspend fun insertStepCount(stepCountData: Long) =
        recordingDatabase.recordingDao().insertStepCount(stepCountData)

}