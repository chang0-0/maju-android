package com.app.majuapp.domain.usecase

import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.domain.repository.LoginRepository
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    val loginResult: StateFlow<NetworkResult<LoginDto>> = loginRepository.loginResult
    suspend fun login(oauthToken: String, fcmToken: String = "") {
        loginRepository.login(oauthToken, fcmToken)
    }

}