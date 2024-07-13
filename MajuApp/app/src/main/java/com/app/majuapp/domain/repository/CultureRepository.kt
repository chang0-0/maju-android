package com.app.majuapp.domain.repository

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.StateFlow

interface CultureRepository {

    val cultureEventList: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>>
    val cultureEventDetail: StateFlow<NetworkResult<NetworkDto<CultureDetailDomainModel>>>

    suspend fun getAllCultureEvents()
    suspend fun getGenreCultureEvents(genre: String)
    suspend fun getCultureEventsDetail(eventId: Int)

} // End of CultureRepository