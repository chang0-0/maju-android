package com.app.majuapp.screen.webview

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.screen.culture.CultureDetailViewModel
import com.app.majuapp.util.NetworkResult
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun WebViewScreen(
    navController: NavHostController,
    cultureDetailViewModel: CultureDetailViewModel
) {
    val cultureEventDetailNetworkResult =
        cultureDetailViewModel.cultureEventDetail.collectAsStateWithLifecycle()
    Box() {
        if (cultureEventDetailNetworkResult.value is NetworkResult.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.Center))
        }
        (cultureEventDetailNetworkResult.value.data as NetworkDto<CultureDetailDomainModel>?)?.data?.let { culture ->
            WebView(
                state = rememberWebViewState(
                    url = culture.url,
                    additionalHttpHeaders = emptyMap()
                )
            )
        }
    }
}