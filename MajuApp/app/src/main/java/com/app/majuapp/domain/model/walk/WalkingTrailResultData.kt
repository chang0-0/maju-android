package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkingTrailResultData(
    @SerializedName("status") val status: Int,
    @SerializedName("statusName") val statusName: String,
    @SerializedName("message") val message: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("data") val data: List<WalkTrailData>
) : Parcelable

