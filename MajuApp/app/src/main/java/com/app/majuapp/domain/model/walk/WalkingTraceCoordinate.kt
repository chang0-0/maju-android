package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkingTraceCoordinate(
    val lat: Double,
    val lon: Double,
) : Parcelable
