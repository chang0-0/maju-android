package com.app.majuapp.domain.api

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.model.walk.WalkingTrailResultData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WalkApi {

    // 산책로 호출
    // Retrieve a trail based on the current request.

    @GET("walking/walking-trails/{lat}/{lon}")
    suspend fun getWalkingTrails(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
    ): Response<WalkingTrailResultData>

    @GET("walking/history/month/{month}")
    suspend fun getWalkingHistoryMonthEvents(@Path("month") date: String): Response<NetworkDto<Map<String, Boolean>>>
//
//    @GET("walking/history/date/{date}")
//    suspend fun getWalkingHistoryDateEvents(@Path("date") date: String): Response<NetworkDto<List<CultureEventDomainModel>>>

} // End of WalkApi interface