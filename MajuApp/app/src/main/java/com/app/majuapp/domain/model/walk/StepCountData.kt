package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class StepCountData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val steps: Long = 0L,
) : Parcelable