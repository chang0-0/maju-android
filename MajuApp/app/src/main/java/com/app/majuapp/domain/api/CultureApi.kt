package com.app.majuapp.domain.api

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.domain.model.CultureEventDomainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CultureApi {

    @GET("culture-events/list")
    suspend fun getAllCultureEvents(): Response<NetworkDto<List<CultureEventDomainModel>>>

    @GET("culture-events/list/{genre}")
    suspend fun getGenreCultureEvents(@Path("genre") genre: String): Response<NetworkDto<List<CultureEventDomainModel>>>

    @GET("culture-events/detail/{eventId}")
    suspend fun getCultureEventsDetail(@Path("eventId") eventId: Int): Response<NetworkDto<CultureDetailDomainModel>>

} // End of CultureApi