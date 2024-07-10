package com.app.majuapp.screen.login

import androidx.lifecycle.ViewModel
import com.app.majuapp.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase
) : ViewModel() {



}