package com.app.majuapp.domain.usecase

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.CultureRepository
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CultureUsecase @Inject constructor(
    private val cultureRepository: CultureRepository
) {

    val cultureEventList: StateFlow<List<CultureEventDomainModel>> = cultureRepository.cultureEventList
    val cultureEventListNetworkResult: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>> = cultureRepository.cultureEventListNetworkResult
    val cultureEventDetail: StateFlow<NetworkResult<NetworkDto<CultureDetailDomainModel>>> = cultureRepository.cultureEventDetail
    val cultureEventToggleNetworkResult: StateFlow<NetworkResult<NetworkDto<Boolean>>> = cultureRepository.cultureEventToggleNetworkResult

    suspend fun getAllCultureEvents() {
        cultureRepository.getAllCultureEvents()
    }

    suspend fun getGenreCultureEvents(genre: String) {
        cultureRepository.getGenreCultureEvents(genre)
    }

    suspend fun getCultureEventsDetail(eventId: Int) {
        cultureRepository.getCultureEventsDetail(eventId)
    }

    suspend fun toggleCultureLike(eventId: Int) {
        cultureRepository.toggleCultureLike(eventId)
    }

} // End of CultureUsecase