package com.app.majuapp.data.dto

import android.os.Parcelable
import com.app.majuapp.domain.model.LoginDomainModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginDto(
    val `data`: LoginDomainModel?,
    val message: String,
    val status: Int,
    val statusName: String?,
    val timestamp: Long,
    val error: String?
) : Parcelable