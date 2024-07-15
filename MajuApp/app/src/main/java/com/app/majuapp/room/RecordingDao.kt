package com.app.majuapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.majuapp.domain.model.walk.StepCountData
import kotlinx.coroutines.flow.Flow


@Dao
interface RecordingDao {

    @Query("SELECT * FROM stepcountdata where id = :id")
    fun getStepCountById(id: Int): Flow<StepCountData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStepCount(stepCountData: Long)

}