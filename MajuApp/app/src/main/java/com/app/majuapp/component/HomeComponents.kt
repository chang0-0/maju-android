package com.app.majuapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.majuapp.ui.theme.defaultPadding

@Composable
fun HomeBox(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().height(160.dp).padding(defaultPadding)
            .background(Color.Blue)
    ) {

    }

} // End of HomeBox()