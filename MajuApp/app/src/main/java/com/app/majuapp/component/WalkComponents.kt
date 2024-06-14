package com.app.majuapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.MajuAppTheme
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.TaupeGray
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.dialogButtonRoundedCorner
import com.app.majuapp.ui.theme.dialogCornerPadding
import com.app.majuapp.ui.theme.dialogDefaultPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkScreenChooseStartDialog(
    title: String,
    content: String,
    onClickDismiss: () -> Unit,
    onClickConfirm: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onClickDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.clip(RoundedCornerShape(dialogCornerPadding)).background(White)
                .padding(
                    start = dialogDefaultPadding,
                    end = dialogDefaultPadding,
                    top = dialogDefaultPadding,
                    bottom = 16.dp
                ),

            ) {
            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = content,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = SonicSilver,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    WalkComponentButton("돌아가기", TaupeGray, onClickDismiss, Modifier.weight(1f))
                    WalkComponentButton("선택하기", GoldenPoppy, onClickConfirm, Modifier.weight(1f))
                }
            }
        }
    }
} // End of WalkScreenChooseStartDialogue()

@Composable
private fun WalkComponentButton(
    buttonText: String, buttonColor: Color, onClickConfirm: () -> Unit, modifier: Modifier
) {
    Button(shape = RoundedCornerShape(dialogButtonRoundedCorner),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        onClick = { onClickConfirm() }) {
        Text(buttonText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
} // End of Button()

@Preview
@Composable
private fun WalkScreenChooseStartDialogPreveiw() {
    MajuAppTheme {
        WalkScreenChooseStartDialog("출발지 선택", "지도 위에 현재 위치\n 선택해주세요!", {}, {})
    }
} // End of WalkScreenChooseStartDialogPreview()
