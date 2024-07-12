package com.app.majuapp.domain.model.walk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponeResultData(
    val status: Int,
    val statusName: String,
    val message: String,
    val timestamp: Long
) : Parcelable