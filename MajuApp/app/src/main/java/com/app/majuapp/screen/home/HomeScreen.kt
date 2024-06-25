package com.app.majuapp.screen.home

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.ExperimentalSafeArgsApi
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.R
import com.app.majuapp.component.HomeScreenRoundedCard
import com.app.majuapp.component.HomeScreenSpacer
import com.app.majuapp.component.Loader
import com.app.majuapp.navigation.Screen
import com.app.majuapp.ui.theme.SkyBlue
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.roundedCornerPadding

private const val TAG = "HomeScreen_창영"

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreenContent(navController)
} // End of HomeScreen()

@Composable
private fun HomeScreenContent(navController: NavController) {
    val context = LocalContext.current
    val brightGrayColor = ContextCompat.getColor(context, R.color.brightGray)

    Surface(modifier = Modifier.fillMaxSize().padding(top = 64.dp)) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = defaultPadding)
        ) {
            item {
                HomeScreenRoundedCard(
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
                HomeScreenRoundedCard(
                    // 홈 화면 알림 카드
                    modifier = Modifier.border(
                        width = 2.dp, color = Color(brightGrayColor), shape = RoundedCornerShape(
                            roundedCornerPadding
                        )
                    ),
                    color = arrayListOf(Color.Transparent, Color.Transparent),
                ) {
                    HomeScreenNoticeBox("")
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
        modifier = Modifier.fillMaxSize().padding(defaultPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
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
private fun HomeScreenNoticeBox(weatherData: String) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(defaultPadding)) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding)).fillMaxWidth()
                .height(120.dp)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                model = ImageRequest.Builder(context)
                    .data("https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg")
                    .crossfade(true).build(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                contentDescription = weatherData,
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "임영웅 콘서트",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Text(
                text = "2024년 6월 11일",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.align(Alignment.Bottom).padding(10.dp)
            )
        }
    }

} // End of HomeScreenNoticeBox()

@OptIn(ExperimentalSafeArgsApi::class)
@Composable
private fun RowOfCategoryBox(categoryList: List<Category>, navController: NavController) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        categoryList.forEach { categoryBox ->
            Box(
                modifier = Modifier.weight(1f).aspectRatio(1f)
                    .clip(RoundedCornerShape(roundedCornerPadding)).clickable {
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
                    modifier = Modifier.background(Color.DarkGray.copy(alpha = 0.4f)).fillMaxSize()
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
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).padding(4.dp))
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