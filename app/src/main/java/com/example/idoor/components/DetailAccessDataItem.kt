package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.ui.theme.CharcoalGray
import com.example.idoor.ui.theme.DarkRed
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun DetailAccessDataItem(
    userName: String,
    email: String,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    date: String? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .background(MidnightBlue)
    ) {
        Column {
            Spacer(Modifier.size(5.dp))
            Text(
                text = userName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = LightGray
            )
            Spacer(Modifier.size(11.dp))
            Text(
                text = email,
                fontWeight = FontWeight.Light,
                fontSize = 11.sp,
                color = LightGray
            )
        }
        Column (
            horizontalAlignment = Alignment.End,
        ){
            ButtonIcon(
                icon = Icons.Default.Delete,
                onClick = onDelete,
                background = DarkRed,
                iconSize = 16.dp,
                containerSize = 30.dp
            )
            Spacer(Modifier.size(10.dp))
            if (date != null) {
                Text(
                    text = date,
                    fontWeight = FontWeight.Light,
                    fontSize = 11.sp,
                    color = LightGray,
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun DetailAccessDataItemPreview() {
    Box(
        modifier = Modifier
            .background(LightGray)
            .padding(16.dp)
    ) {
        DetailAccessDataItem(
            userName = "Alat",
            email = "Master",
            date = "10 September 2023, 10:30",
            onDelete = {}
        )
    }
}