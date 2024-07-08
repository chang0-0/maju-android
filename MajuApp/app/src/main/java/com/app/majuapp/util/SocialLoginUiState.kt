package com.app.majuapp.util

sealed interface SocialLoginUiState {
    data object LoginSuccess: SocialLoginUiState
    data object SocialAppLoginFail: SocialLoginUiState
    data object LoginFail: SocialLoginUiState
    data object Idle: SocialLoginUiState
    data object SocialLogin: SocialLoginUiState
}