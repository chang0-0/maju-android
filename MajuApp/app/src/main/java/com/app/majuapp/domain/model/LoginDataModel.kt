package com.app.majuapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginDataModel(
    val accessToken: String,
    val isExist: Boolean,
    val isLeft: Boolean,
    val refreshToken: String,
    val tokenType: String
) : Parcelable