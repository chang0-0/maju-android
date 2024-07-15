package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkDateHistoryDomainModel(
    val id: Int,
    val distance: Double,
    val steps: Int
): Parcelable
