package com.app.majuapp.domain.repository

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordCalendarRepository {

    val monthEvents: StateFlow<Map<String, BooleanArray>>
    val walkingHistoryMonthEvents: StateFlow<Map<String, Boolean>>
    val cultureLikeMonthEvents: StateFlow<Map<String, Boolean>>
    val cultureLikeDateEvents: StateFlow<List<CultureEventDomainModel>>
    val cultureLikeMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>>
    val cultureLikeDateEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>>
    val walkingHistoryMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>>

    suspend fun getMonthEvents(date: String)
    suspend fun getWalkingHistoryMonthEvents(date: String)
    suspend fun getWalkingHistoryDateEvents(date: String)
    suspend fun getCultureLikeMonthEvents(date: String)
    suspend fun getCultureLikeDateEvents(date: String)

}