package com.app.majuapp.screen.home

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.ExperimentalSafeArgsApi
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.Application
import com.app.majuapp.R
import com.app.majuapp.component.Loader
import com.app.majuapp.component.home.GrayBorderRoundedCard
import com.app.majuapp.component.home.HomeScreenSpacer
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.navigation.Screen
import com.app.majuapp.ui.theme.SkyBlue
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.app.majuapp.util.checkAndRequestPermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

private const val TAG = "HomeScreen_창영"

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    HomeScreenContent(navController, homeViewModel)
    val accessToken = Application.sharedPreferencesUtil.getUserAccessToken()
    val refreshToken = Application.sharedPreferencesUtil.getUserRefreshToken()
    Log.d(TAG, "accessToken: $accessToken")
    Log.d(TAG, "refreshToken: $refreshToken")
} // End of HomeScreen()

@Composable
private fun HomeScreenContent(navController: NavController, homeViewModel: HomeViewModel) {
    // Context
    val context = LocalContext.current
    val brightGrayColor = ContextCompat.getColor(context, R.color.brightGray)

    /** 요청할 권한 **/
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.fold(true) { acc, next -> acc && next }
        /** 권한 요청시 동의 했을 경우 **/
        /** 권한 요청시 동의 했을 경우 **/
        if (areGranted) {
            Log.d("permission", "권한이 동의되었습니다.")
            homeViewModel.getCurrentLocation()
        }
        /** 권한 요청시 거부 했을 경우 **/
        /** 권한 요청시 거부 했을 경우 **/
        else {
            Log.d("permission", "권한이 거부되었습니다.")
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        checkAndRequestPermissions(
            context,
            permissions,
            launcherMultiplePermissions
        )
    }

    val currentLocation = homeViewModel.currentLocation.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = currentLocation.value) {
        Log.d(
            "currentPosition",
            "${currentLocation.value?.latitude}, ${currentLocation.value?.longitude}"
        )
        currentLocation.value.let { it ->
            homeViewModel.getCultureHomeRecommendation(
                it?.latitude.toString() ?: "0",
                it?.longitude.toString() ?: "0"
            )
        }
    }

    val cultureHomeRecommendation =
        homeViewModel.cultureHomeRecommendation.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = defaultPadding)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_logo),
                        tint = Color.Black,
                        contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(30.dp)
                    )
                    IconButton(modifier = Modifier, onClick = {
                        navController.navigate("record_screen") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_default_user),
                            tint = SonicSilver,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(defaultPadding))
                GrayBorderRoundedCard(
                    // 홈 화면 날씨 카드
                    modifier = Modifier,
                    color = arrayListOf(SpiroDiscoBall, SkyBlue),
                ) {
                    HomeScreenWeatherBox("")
                }
                HomeScreenSpacer()
                Text(
                    stringResource(id = R.string.home_screen_how_wether),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(18.dp))
                GrayBorderRoundedCard(
                    // 홈 화면 알림 카드
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = Color(brightGrayColor),
                        shape = RoundedCornerShape(
                            roundedCornerPadding
                        )
                    ),
                    color = arrayListOf(Color.Transparent, Color.Transparent),
                ) {
                    if (cultureHomeRecommendation.value != null)
                        HomeScreenNoticeBox(
                            navController,
                            cultureHomeRecommendation.value!!
                        ) // 피그마 임영웅 박스
                }
                HomeScreenSpacer()
            }
            items(homeCategoryList.chunked(2)) { rowItems ->
                RowOfCategoryBox(rowItems, navController)
            }
        }
    }
} // End of HomeScreenContent()

@Composable
private fun HomeScreenWeatherBox(weatherData: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = defaultPadding, top = defaultPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "서울시 성동구",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = "27℃",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(
                    Alignment.Start
                )
            )
        }
        SubcomposeAsyncImage(
            modifier = Modifier.padding(10.dp),
            model = ImageRequest.Builder(context)
                .data(R.drawable.ic_sunny) // 날씨 아이콘 표시
                .crossfade(true).build(),
            contentScale = ContentScale.Fit,
            contentDescription = weatherData,
        )
    }
} // End of HomeScreenWeatherBox()

@Composable
private fun HomeScreenNoticeBox(
    navController: NavController,
    cultureEventDomainModel: CultureEventDomainModel
) {
    /*
        홈 화면 알림 박스
        Home notice box
        피그마 임영웅 박스
     */

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPadding)
            .clickable() {
                navController.navigate("${Screen.CultureDetail.route}/${cultureEventDomainModel.id}") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(roundedCornerPadding))
                .fillMaxWidth()
                .height(120.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                model = ImageRequest.Builder(context)
                    .data(cultureEventDomainModel.thumbnail)
                    .crossfade(true).build(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                contentDescription = "추천 활동 이미지",
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                cultureEventDomainModel.eventName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .weight(2f),
                maxLines = 1,
            )
            Text(
                text = cultureEventDomainModel.startDate,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Bottom)
                    .padding(horizontal = 10.dp)
            )
        }
    }

} // End of HomeScreenNoticeBox()

@OptIn(ExperimentalSafeArgsApi::class)
@Composable
private fun RowOfCategoryBox(categoryList: List<Category>, navController: NavController) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        categoryList.forEach { categoryBox ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(roundedCornerPadding))
                    .clickable {
                        when (categoryBox.title) {
                            "산책" -> {
                                navController.navigate("walk_screen") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            "문화" -> {
                                navController.navigate(Screen.CultureMap.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                SubcomposeAsyncImage(modifier = Modifier.blur(radius = 1.2.dp),
                    model = ImageRequest.Builder(LocalContext.current).data(categoryBox.imageUrl)
                        .crossfade(true).build(),
                    contentDescription = categoryBox.imageUrl,
                    contentScale = ContentScale.Crop,
                    loading = {
                        Loader()
                    })
                Box(
                    modifier = Modifier
                        .background(Color.DarkGray.copy(alpha = 0.4f))
                        .fillMaxSize()
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                    text = categoryBox.title,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (categoryList.size < 2) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(4.dp)
                )
            }
        }
    }
} // End of NestedLazyVerticalGrid()

private val homeCategoryList = listOf(
    Category(
        "산책",
        "https://images.unsplash.com/photo-1534970028765-38ce47ef7d8d?q=80&w=2264&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "문화",
        "https://images.unsplash.com/photo-1454908027598-28c44b1716c1?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    )
)

data class Category(val title: String, val imageUrl: String)