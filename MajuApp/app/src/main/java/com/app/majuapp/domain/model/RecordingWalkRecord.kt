package com.app.majuapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RecordingWalkRecord(
    val promenadeName: String,
    val distance: Double,
    val stepCount: Int
) : Parcelable