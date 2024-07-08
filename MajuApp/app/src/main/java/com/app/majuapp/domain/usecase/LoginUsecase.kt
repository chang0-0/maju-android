package com.app.majuapp.domain.usecase

import com.app.majuapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val loginRepository: LoginRepository
) {

}