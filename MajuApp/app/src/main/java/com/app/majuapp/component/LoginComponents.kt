package com.app.majuapp.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.majuapp.R
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.util.SocialLoginUiState
import com.app.majuapp.util.findActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    socialLoginViewModel: SocialLoginViewModel,
) {
    val socialLoginUiState by socialLoginViewModel.socialLoginUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current.findActivity()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when (socialLoginUiState) {
        SocialLoginUiState.SocialLogin -> {
            kakaoLogin(socialLoginViewModel = socialLoginViewModel, context = context)
        }
        SocialLoginUiState.LoginFail -> {
            scope.launch {
                snackbarHostState.showSnackbar("카카오 로그인 실패")
            }
            socialLoginViewModel.loginIdle()
        }
        SocialLoginUiState.LoginSuccess -> {
            Log.d("kakao", "로그인 성공")
            scope.launch {
                snackbarHostState.showSnackbar("카카오 로그인 성공")
            }
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
    val TAG = "Kakao Login"

    // 로그인 조합 예제
    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
            socialLoginViewModel.kakaoSocialAppLoginFail()
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            socialLoginViewModel.kakaoLoginSuccess()
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
                socialLoginViewModel.kakaoLoginSuccess()
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context = context, callback = callback)
    }

}