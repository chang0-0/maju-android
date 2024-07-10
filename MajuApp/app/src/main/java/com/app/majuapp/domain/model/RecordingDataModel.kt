package com.app.majuapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordingDataModel(
    val walkRecord: List<RecordingWalkRecord>,
    val cultureLifeRecord: List<CultureLifeRecord>
) : Parcelable