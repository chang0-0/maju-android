package com.app.majuapp.domain.api

import com.app.majuapp.data.dto.LoginDto
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApi {

    @POST("member/kakao-login")
    suspend fun login(
        @Body requestBody: JsonObject
    ): Response<LoginDto>

} // End of LoginApi class