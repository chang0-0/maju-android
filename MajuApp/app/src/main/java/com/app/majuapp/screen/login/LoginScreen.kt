package com.app.majuapp.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.KakaoLoginButton

@Composable
fun LoginScreen(
    navController: NavHostController,
    socialLoginViewModel: SocialLoginViewModel,
    loginViewModel: LoginViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp, bottom = 40.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = "로고 이미지",
                modifier = Modifier
                    .size(288.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.FillWidth
            )
            KakaoLoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                socialLoginViewModel,
                loginViewModel
            )
        }
    }
} // End of LoginScreen

@Preview
@Composable
fun PreviewLoginScreen() {
//    LoginScreen(rememberNavController(), socialLoginViewModel = SocialLoginViewModel())
} // End of PreviewLoginScreen