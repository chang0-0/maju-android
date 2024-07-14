package com.app.majuapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CultureDetailDomainModel(
    val category: String,
    val endDate: String,
    val eventName: String,
    val genre: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val place: String,
    val price: String,
    val startDate: String,
    val thumbnail: String,
    val url: String,
    val likeStatus: Boolean
): Parcelable