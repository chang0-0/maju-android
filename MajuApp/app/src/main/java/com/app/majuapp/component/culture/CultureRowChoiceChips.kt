package com.app.majuapp.component.culture

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.majuapp.component.RowChoiceChips
import com.app.majuapp.screen.culture.CultureViewModel
import com.app.majuapp.util.Constants.GENRES

@Composable
fun CultureRowChoiceChips(
    cultureViewModel: CultureViewModel,
    modifier: Modifier = Modifier
) {
    val genreChoicedIdx = cultureViewModel.genreChoicedIdx.collectAsStateWithLifecycle()
    RowChoiceChips(
        GENRES,
        genreChoicedIdx.value,
        modifier,
    ) { idx ->
        cultureViewModel.genreChoice(idx)
    }
}