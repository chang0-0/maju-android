package com.app.majuapp.domain.api

import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.LoginDomainModel
import retrofit2.Response
import retrofit2.http.GET

interface ReissueApi {

    @GET("member/reissue")
    suspend fun reissue(): Response<NetworkDto<LoginDomainModel>>

}