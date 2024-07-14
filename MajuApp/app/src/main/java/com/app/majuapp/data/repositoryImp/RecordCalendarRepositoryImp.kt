package com.app.majuapp.data.repositoryImp

import android.util.Log
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.api.CultureApi
import com.app.majuapp.domain.api.WalkApi
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.RecordCalendarRepository
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.setNetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RecordCalendarRepositoryImp @Inject constructor(
    private val cultureApi: CultureApi,
    private val walkApi: WalkApi,
): RecordCalendarRepository {

    private val _monthEvents: MutableStateFlow<Map<String, BooleanArray>> = MutableStateFlow(
        mapOf()
    )
    override val monthEvents = _monthEvents.asStateFlow()
    private val _walkingHistoryMonthEvents = MutableStateFlow<Map<String, Boolean>>(mapOf())
    override val walkingHistoryMonthEvents: StateFlow<Map<String, Boolean>> = _walkingHistoryMonthEvents.asStateFlow()
    private val _cultureLikeMonthEvents: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(
        mapOf()
    )
    override val cultureLikeMonthEvents: StateFlow<Map<String, Boolean>> = _cultureLikeMonthEvents.asStateFlow()
    private val _cultureLikeDateEvents: MutableStateFlow<List<CultureEventDomainModel>> = MutableStateFlow(
        listOf()
    )
    override val cultureLikeDateEvents: StateFlow<List<CultureEventDomainModel>> = _cultureLikeDateEvents.asStateFlow()
    private val _cultureLikeMonthEventsNetworkResult = MutableStateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>>(NetworkResult.Idle())
    override val cultureLikeMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>> = _cultureLikeMonthEventsNetworkResult.asStateFlow()
    private val _cultureLikeDateEventsNetworkResult = MutableStateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>>(NetworkResult.Idle())
    override val cultureLikeDateEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>> = _cultureLikeDateEventsNetworkResult.asStateFlow()
    private val _walkingHistoryMonthEventsNetworkResult = MutableStateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>>(NetworkResult.Idle())
    override val walkingHistoryMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>> = _walkingHistoryMonthEventsNetworkResult.asStateFlow()

    override suspend fun getMonthEvents(date: String) {
        var map = mutableMapOf<String, BooleanArray>()
        walkingHistoryMonthEvents.value.forEach { k, v ->
            map.put(k, booleanArrayOf(v, cultureLikeMonthEvents.value.getOrDefault(k, false)))
        }
        _monthEvents.value = map
    }

    override suspend fun getWalkingHistoryMonthEvents(date: String) {
        val response = walkApi.getWalkingHistoryMonthEvents(date)
        _walkingHistoryMonthEventsNetworkResult.setNetworkResult(response) {
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        _walkingHistoryMonthEvents.value = it.data!!
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override suspend fun getWalkingHistoryDateEvents(date: String) {
//        TODO("Not yet implemented")
    }

    override suspend fun getCultureLikeMonthEvents(date: String){
        val response = cultureApi.getCultureLikeMonthEvents(date)
        _cultureLikeMonthEventsNetworkResult.setNetworkResult(response) {
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        Log.d("Calendar-repo", it.data.toString())
                        _cultureLikeMonthEvents.value = it.data!!
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override suspend fun getCultureLikeDateEvents(date: String) {
        val response = cultureApi.getCultureLikeDateEvents(date)
        _cultureLikeDateEventsNetworkResult.setNetworkResult(response) {
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        _cultureLikeDateEvents.value = it.data!!
                    }
                    else -> {

                    }
                }
            }
        }
    }

}