package com.app.majuapp.screen.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.app.majuapp.Application
import com.app.majuapp.R
import com.app.majuapp.component.KakaoLoginButton
import com.app.majuapp.component.TAG
import com.app.majuapp.data.dto.LoginDto
import com.app.majuapp.navigation.Screen
import com.app.majuapp.util.NetworkResult

@Composable
fun LoginScreen(
    navController: NavHostController,
    socialLoginViewModel: SocialLoginViewModel,
    loginViewModel: LoginViewModel
) {

    val loginResult by loginViewModel.loginResult.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = loginResult) {
        loginResult.let {
            when (it) {
                is NetworkResult.Success<LoginDto> -> {
                    Log.d(
                        TAG, "서버 통신 로그인 성공"
                    )

                    it.value.data?.let { loginDto ->
                        Application.sharedPreferencesUtil.apply {
                            addUserAccessToken(loginDto.accessToken)
                            addUserRefreshToken(loginDto.refreshToken)
                        }
                        navController.navigate(Screen.Home.route)
                    }
                }

                is NetworkResult.Error -> {
                    Log.e(TAG, "서버 통신 에러 ${it.msg}")
                }

                is NetworkResult.Loading -> {
                    Log.d(TAG, "서버 통신 로딩 중")
                }

                is NetworkResult.Idle -> {
                    Log.d(TAG, "서버 통신 대기 중")
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp, bottom = 40.dp)
    ) {
        Box {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_logo),
                    contentDescription = "로고 이미지",
                    modifier = Modifier.size(288.dp),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
//                    Spacer(modifier = Modifier.weight(1f))
                    if (loginResult is NetworkResult.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    KakaoLoginButton(
                        modifier = Modifier.fillMaxWidth(), socialLoginViewModel, loginViewModel
                    )
                }
            }
        }
    }
} // End of LoginScreen

@Preview
@Composable
fun PreviewLoginScreen() {
//    LoginScreen(rememberNavController(), socialLoginViewModel = SocialLoginViewModel())
} // End of PreviewLoginScreen