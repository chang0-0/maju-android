package com.app.majuapp.data.repositoryImp

import com.app.majuapp.domain.api.LoginApi
import com.app.majuapp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val loginApi: LoginApi) : LoginRepository {

    override fun login() {

    }

}