package com.app.majuapp.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.domain.usecase.LoginUsecase
import com.app.majuapp.domain.usecase.ReissueUsecase
import com.app.majuapp.util.NetworkResult
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase, private val reissueUsecase: ReissueUsecase
) : ViewModel() {
    val loginResult: StateFlow<NetworkResult<LoginDto>> = loginUsecase.loginResult

    fun login(oAuthToken: String, fcmToken: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            loginUsecase.login(oAuthToken, fcmToken)
        }
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        loginUsecase.logout()
    }

    fun idle() = viewModelScope.launch(Dispatchers.IO) {
        loginUsecase.idle()
    }

    fun reissue() = viewModelScope.launch(Dispatchers.IO) {
        reissueUsecase.reissue()
    }

} // End of LoginViewModel class