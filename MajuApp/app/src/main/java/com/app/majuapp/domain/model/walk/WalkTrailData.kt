package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkTrailData(
    val id: Int,
    val name: String,
    val level: String,
    val startLat: Double,
    val StartLon: Double,
    val endLat: Double,
    val EndLon: Double,
) : Parcelable