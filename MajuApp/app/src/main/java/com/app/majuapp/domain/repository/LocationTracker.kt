package com.app.majuapp.domain.repository

import android.location.Location

interface LocationTracker {

    suspend fun getCurrentLocation(): Location?

}