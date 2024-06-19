package com.app.majuapp.screen.preference

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.R
import com.app.majuapp.component.Loader
import com.app.majuapp.data.model.SelectableImage
import com.app.majuapp.navigation.Screen
import com.app.majuapp.ui.theme.DullSpiroDiscoBall
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid

@Composable
fun PreferenceScreen(
    navController: NavController = rememberNavController()
) {
    //TODO by _root_ide_package_.androidx.compose.runtime.remember() REAL DATA
    val dummyList = List(9) {
        SelectableImage("$it", "", "https://images.unsplash.com/photo-1454908027598-28c44b1716c1?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 80.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        /*TODO go back function */
                    }
            )
            Box(modifier = Modifier.height(24.dp))
            Text(
                stringResource(id = R.string.preference_screen_hellow),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                // style = MaterialTheme.typography.bodyLarge,
                style = TextStyle(color = Color.Black)
            )
            Box(modifier = Modifier.height(8.dp))
            Text(
                stringResource(id = R.string.preference_screen_preference_question),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                // style = MaterialTheme.typography.bodyLarge,
                style = TextStyle(color = Color.Black)
            )
            Box(modifier = Modifier.weight(1f))
            SelectableCategoryGrid(
                dummyList
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SpiroDiscoBall),
                onClick = {
                    /*TODO register account and go home function */
                    navController.navigate(Screen.Home.route)
                }) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    text = "다음",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
} // End of PreferenceScreen

@Composable
fun SelectableCategoryGrid(
    selectableImages: List<SelectableImage> = listOf(),
    modifier: Modifier = Modifier,
) {
    VerticalGrid(
        columns = SimpleGridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        for ((index, item) in selectableImages.withIndex()) {
            SelectableImageButton(
                selectableImage = item,
                item.isSelectedState
            ) {
                Log.d("recomposition", "clicked")
                item.toggle()
                Log.d("recomposition", "after Clicked ${item.isSelectedState}")
            }
        }
    }
} // End of CategoryGrid

@Composable
fun SelectableImageButton(
    selectableImage: SelectableImage,
    selectState: Boolean,
    onSelected: () -> Unit = {}
) {
    val roundedCornerSize = 16.dp
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(roundedCornerSize))
            .background(Color.LightGray)
            .clickable {
                onSelected()
            }
    ) {
        SubcomposeAsyncImage(modifier = Modifier
            .blur(radius = 1.2.dp),
            model = ImageRequest.Builder(LocalContext.current).data(selectableImage.imageUrl)
                .crossfade(true).build(),
            contentDescription = selectableImage.desc,
            contentScale = ContentScale.Crop,
            loading = {
                Loader()
            })
        if (selectState) {
            Box(
                modifier = Modifier
                    .background(SpiroDiscoBall.copy(alpha = 0.6f))
                    .border(width = 3.dp, color = DullSpiroDiscoBall, shape = RoundedCornerShape(roundedCornerSize))
                    .fillMaxSize()
            )
        }
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp,
            text = selectableImage.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PreviewPreferenceScreen() {
    PreferenceScreen()
} // End of PreviewPreferenceScreen