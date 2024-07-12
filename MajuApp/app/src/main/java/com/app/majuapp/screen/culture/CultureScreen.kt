package com.app.majuapp.screen.culture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.component.RowChoiceChips
import com.app.majuapp.component.culture.CultureCard
import com.app.majuapp.component.culture.CultureRowChoiceChips
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureDomainModel
import com.app.majuapp.navigation.Screen
import com.app.majuapp.util.Constants.GENRES
import com.app.majuapp.util.dummyList

@Composable
fun CultureScreen(
    navController: NavHostController,
    cultureViewModel: CultureViewModel
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {
        val cultureEventListNetworkResult = cultureViewModel.cultureEventList.collectAsStateWithLifecycle()

        Column {
            CultureRowChoiceChips(cultureViewModel = cultureViewModel, modifier = Modifier)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                (cultureEventListNetworkResult.value.data as NetworkDto<List<CultureDomainModel>>?)?.let {
                    items(it.data ?: listOf()) { culture ->
                        CultureCard(culture) { id ->
                            navController.navigate("${Screen.CultureDetail.route}/$id")
                        }
                    }
                }
            }

        }

    }


} // End of CultureScreen

@Preview
@Composable
fun PreviewCultureScreen() {
//    CultureScreen(navController = rememberNavController(), CultureViewModel())
}