package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkTrailData(
    val id: Int,
    val name: String,
    val level: String,
    val startLat: Double,
    val startLon: Double,
    val endLat: Double,
    val endLon: Double,
) : Parcelable