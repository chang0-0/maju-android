package com.app.majuapp.data.repositoryImp

import androidx.compose.runtime.mutableStateOf
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.api.CultureApi
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.repository.HomeCultureRepository
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.setNetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HomeCultureRepositoryImp @Inject constructor(
    private val cultureApi: CultureApi,
): HomeCultureRepository {

    private val _cultureHomeRecommendation: MutableStateFlow<CultureEventDomainModel?> = MutableStateFlow(null)
    override val cultureHomeRecommendation: StateFlow<CultureEventDomainModel?> = _cultureHomeRecommendation.asStateFlow()
    private val _cultureHomeRecommendationNetworkResult: MutableStateFlow<NetworkResult<NetworkDto<CultureEventDomainModel?>>> = MutableStateFlow(NetworkResult.Idle())
    override val cultureHomeRecommendationNetworkResult: StateFlow<NetworkResult<NetworkDto<CultureEventDomainModel?>>> = _cultureHomeRecommendationNetworkResult.asStateFlow()

    override suspend fun getCultureHomeRecommendation(lat: String, lon: String) {
        val response = cultureApi.getCultureHomeRecommendation(lat, lon)
        _cultureHomeRecommendationNetworkResult.setNetworkResult(response) {
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        _cultureHomeRecommendation.value = it.data!!
                    }
                    else -> {

                    }
                }
            }
        }
    }


}