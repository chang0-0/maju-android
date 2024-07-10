package com.app.majuapp.domain.api

import com.app.majuapp.data.dto.LoginDto
import retrofit2.Response
import retrofit2.http.POST


interface LoginApi {

    @POST("member/kakao-login")
    fun login(): Response<LoginDto>

}