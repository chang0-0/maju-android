package com.app.majuapp.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.R
import com.app.majuapp.component.HomeScreenRoundedCard
import com.app.majuapp.component.HomeScreenSpacer
import com.app.majuapp.component.Loader
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.roundedCornerPadding

private const val TAG = "HomeScreen_창영"

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreenContent()
} // End of HomeScreen()

@Composable
private fun HomeScreenContent() {
    val context = LocalContext.current
    val brightGrayColor = ContextCompat.getColor(context, R.color.bright_gray)

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = defaultPadding),
        contentPadding = PaddingValues(horizontal = defaultPadding)
    ) {
        item {
            HomeScreenRoundedCard(
                modifier = Modifier, color = CardDefaults.cardColors(containerColor = Color.Blue)
            )
            HomeScreenSpacer()
            Text(
                stringResource(id = R.string.home_screen_how_wether),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            HomeScreenSpacer()
            HomeScreenRoundedCard(
                modifier = Modifier.border(
                    width = 2.dp, color = Color(brightGrayColor), shape = RoundedCornerShape(
                        roundedCornerPadding
                    )
                ), color = CardDefaults.cardColors(containerColor = Color.Transparent)
            )
            HomeScreenSpacer()
        }
        items(homeCategoryList.chunked(2)) { rowItems ->
            RowOfCategoryBox(rowItems)
        }
    }
} // End of HomeScreenContent()

@Composable
private fun RowOfCategoryBox(categoryList: List<Category>) {
    Log.d(TAG, "RowOfCategoryBox: $categoryList")

    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        categoryList.forEach { categoryBox ->
            Box(
                modifier = Modifier.weight(1f).aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(modifier = Modifier.clip(
                    RoundedCornerShape(
                        roundedCornerPadding
                    )
                )
                    .clickable {

                    },
                    model = ImageRequest.Builder(LocalContext.current).data(categoryBox.imageUrl)
                        .crossfade(true).build(),
                    contentDescription = categoryBox.imageUrl,
                    contentScale = ContentScale.Crop,
                    loading = {
                        Loader()
                    })
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

//    LazyVerticalGrid(
//        modifier = Modifier.padding(bottom = 10.dp)
//            .wrapContentHeight(),
//        horizontalArrangement = Arrangement.spacedBy(10.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        columns = GridCells.Fixed(2),
//        userScrollEnabled = false,
//    ) {
//        items(homeCategoryList.size, span = { index -> GridItemSpan(1) }) { index ->
//            HomeScreenCategoryItem(
//                modifier = Modifier,
//                categoryItem = homeCategoryList[index],
//            ) {
//
//            }
//        }
//    }
} // End of NestedLazyVerticalGrid()

@Composable
@Preview
private fun HomeScreenPreview() {
    Surface(modifier = Modifier.background(Color.White)) {
        HomeScreenContent()
    }
} // End of HomeScreenPreview()

private val homeCategoryList = listOf(
    Category(
        "산책",
        "https://images.unsplash.com/photo-1534970028765-38ce47ef7d8d?q=80&w=2264&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "문화",
        "https://images.unsplash.com/photo-1454908027598-28c44b1716c1?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "등산",
        "https://images.unsplash.com/photo-1551632811-561732d1e306?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "전시회",
        "https://images.unsplash.com/photo-1518998053901-5348d3961a04?q=80&w=2274&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "전시회",
        "https://images.unsplash.com/photo-1518998053901-5348d3961a04?q=80&w=2274&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "전시회",
        "https://images.unsplash.com/photo-1518998053901-5348d3961a04?q=80&w=2274&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "전시회",
        "https://images.unsplash.com/photo-1518998053901-5348d3961a04?q=80&w=2274&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "전시회",
        "https://images.unsplash.com/photo-1518998053901-5348d3961a04?q=80&w=2274&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Category(
        "전시회",
        "https://images.unsplash.com/photo-1518998053901-5348d3961a04?q=80&w=2274&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),

    )

data class Category(val title: String, val imageUrl: String)