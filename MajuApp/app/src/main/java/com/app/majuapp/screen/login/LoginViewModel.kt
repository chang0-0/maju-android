package com.app.majuapp.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.domain.usecase.LoginUsecase
import com.app.majuapp.util.NetworkResult
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase
) : ViewModel() {
    val loginResult: StateFlow<NetworkResult<LoginDto>> = loginUsecase.loginResult

    fun login(oAuthToken: String, fcmToken: String = "") {
        viewModelScope.launch {
            loginUsecase.login(oAuthToken, fcmToken)
        }
    }

}