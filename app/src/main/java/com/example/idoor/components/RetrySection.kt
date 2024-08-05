package com.example.idoor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.R
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit,
    textColor: Color = LightGray
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error,
            fontSize = 14.sp,
            color = textColor,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(10.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SteelBlue,
                contentColor = LightGray
            )
        ) {
            Text(
                text = stringResource(id = R.string.btn_retry),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}