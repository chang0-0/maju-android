package com.app.majuapp.data.repositoryImp

import com.app.majuapp.Application
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.api.ReissueApi
import com.app.majuapp.domain.model.LoginDomainModel
import com.app.majuapp.domain.repository.ReissueRepository
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.setNetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ReissueRepositoryImp @Inject constructor(private val reissueApi: ReissueApi): ReissueRepository {

    private val _reissueNetworkResult = MutableStateFlow<NetworkResult<NetworkDto<LoginDomainModel>>>(NetworkResult.Idle())
    val reissueNetworkResult: StateFlow<NetworkResult<NetworkDto<LoginDomainModel>>> = _reissueNetworkResult

    override suspend fun reissue() {
        val response = reissueApi.reissue()
        _reissueNetworkResult.setNetworkResult(response) {
            response.body()?.let {
                when(it.status) {
                    200 -> {
                        Application.sharedPreferencesUtil.addUserAccessToken(it.data!!.accessToken)
                    }
                    else -> {

                    }
                }
            }
        }
    }
}