package com.app.majuapp.data.repositoryImp

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.api.CultureApi
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.CultureRepository
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.setNetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CultureRepositoryImp @Inject constructor(
    private val cultureApi: CultureApi
) : CultureRepository {

    private val _cultureEventList = MutableStateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>>(NetworkResult.Idle())
    override val cultureEventList: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>> = _cultureEventList

    private val _cultureEventDetail = MutableStateFlow<NetworkResult<NetworkDto<CultureDetailDomainModel>>>(NetworkResult.Idle())
    override val cultureEventDetail: StateFlow<NetworkResult<NetworkDto<CultureDetailDomainModel>>> = _cultureEventDetail

    override suspend fun getAllCultureEvents() {
        val response = cultureApi.getAllCultureEvents()
        _cultureEventList.setNetworkResult(response)
    }

    override suspend fun getGenreCultureEvents(genre: String) {
        val response = cultureApi.getGenreCultureEvents(genre)
        _cultureEventList.setNetworkResult(response)
    }

    override suspend fun getCultureEventsDetail(eventId: Int) {
        val response = cultureApi.getCultureEventsDetail(eventId)
        _cultureEventDetail.setNetworkResult(response)
    }
} // End of CultureRepositoryImp