package com.app.majuapp.domain.api

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.walk.WalkDateHistoryDomainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WalkApi {


    @GET("walking/history/month/{month}")
    suspend fun getWalkingHistoryMonthEvents(@Path("month") date: String): Response<NetworkDto<Map<String, Boolean>>>

    @GET("walking/history/date/{date}")
    suspend fun getWalkingHistoryDateEvents(@Path("date") date: String): Response<NetworkDto<List<WalkDateHistoryDomainModel>>>

} // End of WalkApi interface