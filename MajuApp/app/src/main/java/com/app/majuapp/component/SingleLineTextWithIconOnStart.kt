package com.app.majuapp.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SingleLineTextWithIconOnStart(
    textContent: String,
    iconDescription: String,
    imageVector: ImageVector,
    iconTint: Color,
    size: Int,
    intervalSize: Dp
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = imageVector,
            contentDescription = iconDescription,
            modifier = Modifier.size((size + 2).dp),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(intervalSize))
        Text(
            fontWeight = FontWeight.Medium,
            fontSize = size.sp,
            text = textContent,
            textAlign = TextAlign.Start,
            maxLines = 1
        )
    }
} // End of SingleLineTextWithIconOnStart