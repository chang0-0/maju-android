package com.app.majuapp.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.majuapp.R
import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.SocialLoginUiState
import com.app.majuapp.util.findActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch

val TAG = "Kakao Login"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    socialLoginViewModel: SocialLoginViewModel,
    loginViewModel: LoginViewModel,
) {
    val socialLoginUiState by socialLoginViewModel.socialLoginUiState.collectAsStateWithLifecycle()
    val loginResult by loginViewModel.loginResult.collectAsStateWithLifecycle()
    val context = LocalContext.current.findActivity()

    LaunchedEffect(key1 = loginResult) {
        loginResult.let {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d(
                        TAG,
                        "서버 통신 로그인 성공 ${it.value.data?.accessToken}"
                    )
                }
                is NetworkResult.Error -> {Log.e(TAG, "서버 통신 에러 ${it.msg}")}
                is NetworkResult.Loading -> {Log.e(TAG, "서버 통신 대기 중")}
            }
        }

    }

    when (socialLoginUiState) {
        SocialLoginUiState.SocialLogin -> {
            kakaoLogin(socialLoginViewModel = socialLoginViewModel, context = context)
        }

        SocialLoginUiState.LoginFail -> {
            Log.d(TAG, "로그인 실패")
            socialLoginViewModel.loginIdle()
        }

        SocialLoginUiState.LoginSuccess -> {
            Log.d(TAG, "로그인 성공")
            loginViewModel.login(socialLoginViewModel.oAuthToken)
            socialLoginViewModel.loginIdle()
        }

        else -> {}
    }

    Image(
        painter = painterResource(id = R.drawable.kakao_login_large_wide),
        contentDescription = "카카오 로그인",
        modifier = modifier.clickable {
            /* TODO */
            socialLoginViewModel.kakaoLogin()
        },
        contentScale = ContentScale.FillWidth
    )
} // End of KakaoLoginButton

private fun kakaoLogin(
    socialLoginViewModel: SocialLoginViewModel,
    context: Context
) {

    // 로그인 조합 예제
    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
            socialLoginViewModel.kakaoSocialAppLoginFail()
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            socialLoginViewModel.kakaoLoginSuccess(token.accessToken)
        }
    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
        UserApiClient.instance.loginWithKakaoTalk(context = context) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)
                socialLoginViewModel.kakaoSocialAppLoginFail()
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context = context, callback = callback)
            } else if (token != null) {
                Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                socialLoginViewModel.kakaoLoginSuccess(token.accessToken)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context = context, callback = callback)
    }

}