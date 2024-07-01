package com.app.majuapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.majuapp.R
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.ui.theme.dialogButtonRoundedCorner
import com.kakao.sdk.user.UserApiClient

@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel? = null,
) {

    Image(
        painter = painterResource(id = R.drawable.kakao_login_large_wide),
        contentDescription = "카카오 로그인",
        modifier = modifier.clickable {
            /* TODO */
            loginViewModel?.kakaoLogin()
        },
        contentScale = ContentScale.FillWidth
    )
} // End of KakaoLoginButton

@Preview
@Composable
fun PreviewKakaoLoginButton() {
    KakaoLoginButton()
}