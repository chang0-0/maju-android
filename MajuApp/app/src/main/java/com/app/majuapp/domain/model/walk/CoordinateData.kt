package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordinateData(
    // 처음 좌표가 없을 경우를 위해 nullable
    val lat: Double?,
    val lng: Double?
) : Parcelable
