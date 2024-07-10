package com.app.majuapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CultureLifeRecord(
    val imageUrl: String,
    val type: String,
    val name: String,
    val location: String,
    val date: String
) : Parcelable
