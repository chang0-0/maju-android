package com.app.majuapp.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextLayoutResult
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import kotlin.math.roundToInt

fun textCenterAlignment(
    textLayoutResult: TextLayoutResult?,
    lineCount: Int,
): Alignment.Vertical = Alignment.Vertical { size, space ->
    val height = space - size
    val center = (height / 2f).roundToInt() // Alignment.CenterVertically 참고
    when {
        textLayoutResult == null -> center // 텍스트가 그려지지 않았다면
        textLayoutResult.lineCount <= 1 -> center // 텍스트가 한 줄 이라면
        else -> { // 텍스트가 두 줄 이상이라면
            val textHeight = textLayoutResult.size.height
            val lineTop = textLayoutResult.getLineTop(lineCount)
            val lineBottom = textLayoutResult.getLineBottom(lineCount)
            val lineCenter = lineTop + (lineBottom - lineTop) / 2
            (height - textHeight) / 2 + lineCenter.roundToInt()
        }
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun bitmapDescriptorUsingVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}