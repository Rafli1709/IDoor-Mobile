package com.example.idoor.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.ui.theme.LightGray


@Composable
fun ModalItem(
    attribute: String,
    value: String,
    textColor: Color = LightGray,
    fontSize: TextUnit = 12.sp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = attribute,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = textColor,
            modifier = Modifier.weight(3f)
        )
        Text(
            text = ":",
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = textColor
        )
        Spacer(modifier = Modifier.size(7.dp))
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = textColor,
            modifier = Modifier.weight(7f)
        )
    }
}