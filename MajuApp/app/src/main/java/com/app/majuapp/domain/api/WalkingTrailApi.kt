package com.app.majuapp.domain.api

import com.app.majuapp.domain.model.walk.WalkingTrailTraceData
import com.app.majuapp.domain.model.walk.WalkingTrailResultData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WalkingTrailApi {
    // 산책로 호출
    // Retrieve a trail based on the current request.
    @GET("walking/walking-trails/{lat}/{lon}")
    suspend fun getWalkingTrails(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
    ): Response<WalkingTrailResultData>

    // 경로 호출
    @GET("walking/tmap-trace/{startLat}/{startLon}/{endLat}/{endLon}")
    suspend fun getWalkingTrailTrace(
        @Path("startLat") startLat: Double,
        @Path("startLon") startLon: Double,
        @Path("endLat") endLat: Double,
        @Path("endLon") endLon: Double,
    ): Response<WalkingTrailTraceData>


} // End of WalkingTrailApi interface