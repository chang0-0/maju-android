package com.app.majuapp.domain.usecase

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.HomeCultureRepository
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HomeCultureUsecase @Inject constructor(
    private val homeCultureRepository: HomeCultureRepository
) {

    val cultureHomeRecommendation: StateFlow<CultureEventDomainModel?> = homeCultureRepository.cultureHomeRecommendation
    val cultureHomeRecommendationNetworkResult: StateFlow<NetworkResult<NetworkDto<CultureEventDomainModel?>>> = homeCultureRepository.cultureHomeRecommendationNetworkResult

    suspend fun getCultureHomeRecommendation(lat: String, lon: String) = homeCultureRepository.getCultureHomeRecommendation(lat, lon)

}