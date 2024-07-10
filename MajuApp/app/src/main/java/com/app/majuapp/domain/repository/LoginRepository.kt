package com.app.majuapp.domain.repository

import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginRepository {

    val loginResult: StateFlow<NetworkResult<LoginDto>>
    suspend fun login(oauthToken: String, fcmToken: String)

}