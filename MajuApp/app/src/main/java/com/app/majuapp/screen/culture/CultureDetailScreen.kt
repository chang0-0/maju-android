package com.app.majuapp.screen.culture

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.culture.CultureDetailButton
import com.app.majuapp.component.culture.CultureDetailCategoryChip
import com.app.majuapp.component.MultiLineTextWithIconOnStart
import com.app.majuapp.component.NetworkImageCard
import com.app.majuapp.component.SingleLineTextWithIconOnStart
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.data.repositoryImp.CultureRepositoryImp
import com.app.majuapp.domain.api.CultureApi
import com.app.majuapp.domain.model.CultureDetailDomainModel
import com.app.majuapp.domain.usecase.CultureUsecase
import com.app.majuapp.navigation.Screen
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.Gray
import com.app.majuapp.ui.theme.cultureDetailIntervalSize
import com.app.majuapp.ui.theme.cultureDetailMediumSpacerSize
import com.app.majuapp.ui.theme.cultureDetailSmallSpacerSize
import com.app.majuapp.ui.theme.cultureDetailTextWithIconSize
import com.app.majuapp.ui.theme.cultureDetailTitleFontSize
import com.app.majuapp.util.dummyList
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun CultureDetailScreen(
    navController: NavHostController,
    cultureEventId: Int,
    cultureDetailViewModel: CultureDetailViewModel
) {

    val cultureEventDetailNetworkResult = cultureDetailViewModel.cultureEventDetail.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        cultureDetailViewModel.getCultureEventDetail(cultureEventId)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            (cultureEventDetailNetworkResult.value.data as NetworkDto<CultureDetailDomainModel>?)?.data?.let { culture ->
                NetworkImageCard(
                    networkUrl = culture.thumbnail,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CultureDetailCategoryChip(cultureDetailCategory = culture.category)
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.icon_favorite_description)
                        )
                    }
                    Spacer(modifier = Modifier.height(cultureDetailMediumSpacerSize))
                    Text(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = cultureDetailTitleFontSize,
                        text = culture.eventName,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(cultureDetailMediumSpacerSize))
                    SingleLineTextWithIconOnStart(
                        textContent = culture.place,
                        iconDescription = stringResource(id = R.string.icon_location_description),
                        imageVector = Icons.Outlined.LocationOn,
                        iconTint = GoldenPoppy,
                        size = cultureDetailTextWithIconSize,
                        intervalSize = cultureDetailIntervalSize
                    )
                    Spacer(modifier = Modifier.height(cultureDetailSmallSpacerSize))
                    SingleLineTextWithIconOnStart(
                        textContent = "${culture.startDate} ~ ${culture.endDate}",
                        iconDescription = stringResource(id = R.string.icon_time_description),
                        imageVector = Icons.Outlined.AccessTime,
                        iconTint = GoldenPoppy,
                        size = cultureDetailTextWithIconSize,
                        intervalSize = cultureDetailIntervalSize
                    )
                    Spacer(modifier = Modifier.height(cultureDetailSmallSpacerSize))
                    MultiLineTextWithIconOnStart(
                        textContent = culture.price,
                        iconDescription = stringResource(id = R.string.icon_money_info_description),
                        imageVector = Icons.Outlined.MonetizationOn,
                        iconTint = GoldenPoppy,
                        size = cultureDetailTextWithIconSize,
                        intervalSize = cultureDetailIntervalSize
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(cultureDetailMediumSpacerSize),
                    modifier = Modifier.padding(vertical = 28.dp),
                ) {
                    CultureDetailButton(
                        buttonText = stringResource(id = R.string.go_back),
                        buttonColor = Gray,
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CultureDetailButton(
                        buttonText = stringResource(id = R.string.homepage),
                        buttonColor = GoldenPoppy,
                        onClick = {
                            navController.navigate(Screen.WebView.route)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

} // End of CultureDetailScreen


@Preview
@Composable
fun PreviewCultureDetailScreen() {
//    CultureDetailScreen(
//        rememberNavController(),
//        1
//    )
} // End of PreviewPreferenceScreen