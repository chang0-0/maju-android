package com.app.majuapp.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.util.textCenterAlignment

@Composable
fun MultiLineTextWithIconOnStart(
    textContent: String,
    iconDescription: String,
    imageVector: ImageVector,
    iconTint: Color,
    size: Int,
    intervalSize: Dp
) {

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult, 0)
    }
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = iconDescription,
            modifier = Modifier
                .align(alignment)
                .size((size + 2).dp),
            tint = GoldenPoppy
        )
        Spacer(modifier = Modifier.width(intervalSize))
        Text(
            fontWeight = FontWeight.Medium,
            fontSize = size.sp,
            text = textContent,
            onTextLayout = { textLayoutResult = it },
        )
    }
} // End of MultiLineTextWithIconOnStart