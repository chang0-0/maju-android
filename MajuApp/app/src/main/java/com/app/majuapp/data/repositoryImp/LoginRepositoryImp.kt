package com.app.majuapp.data.repositoryImp

import android.util.Log
import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.domain.api.LoginApi
import com.app.majuapp.domain.repository.LoginRepository
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.handleFlowApi
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

val TAG = "login_repo_imp"

class LoginRepositoryImp @Inject constructor(private val loginApi: LoginApi) : LoginRepository {

    private val _loginResult = MutableStateFlow<NetworkResult<LoginDto>>(NetworkResult.Loading())
    override val loginResult: StateFlow<NetworkResult<LoginDto>> = _loginResult

    override suspend fun login(oauthToken: String, fcmToken: String) {
        val response = loginApi.login(
            JsonObject().apply {
                addProperty("accessToken", oauthToken)
                addProperty("fcmToken", fcmToken)
            }
        )
        _loginResult.emit(NetworkResult.Loading())
        try {
            when {
                response.isSuccessful -> {
                    _loginResult.emit(
                        NetworkResult.Success(
                            response.body()!!
                        )
                    )
                }
                response.errorBody() != null -> _loginResult.emit(
                    NetworkResult.Error(
                        response.code(),
                        response.errorBody().toString(),
                    )
                )
            }
        } catch (e: java.lang.Exception) {
            _loginResult.emit(
                NetworkResult.Error(
                    999,
                    e.message,
                    e
                )
            )
            Log.e(TAG, "login repo err ${e.message}")
        } // End of try-catch
    }

}