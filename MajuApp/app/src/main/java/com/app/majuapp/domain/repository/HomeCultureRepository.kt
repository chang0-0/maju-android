package com.app.majuapp.domain.repository

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.model.walk.WalkDateHistoryDomainModel
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.StateFlow

interface HomeCultureRepository {

    val cultureHomeRecommendation: StateFlow<CultureEventDomainModel?>
    val cultureHomeRecommendationNetworkResult: StateFlow<NetworkResult<NetworkDto<CultureEventDomainModel?>>>

    suspend fun getCultureHomeRecommendation(lat: String, lon: String)

}