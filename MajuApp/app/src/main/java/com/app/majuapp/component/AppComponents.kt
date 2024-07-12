package com.app.majuapp.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Loader(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            strokeWidth = 3.dp
        )
    }
} // End of Loader()

@Composable
fun fillMaxWidthSpacer(modifier: Modifier, height: Dp) {
    Spacer(modifier = modifier.fillMaxWidth().height(height))
} // End of fillMaxWidthSpacer()