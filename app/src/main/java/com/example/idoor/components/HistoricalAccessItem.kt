package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun HistoricalAccessItem(
    toolsName: String,
    userName: String,
    type: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Row (
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .background(MidnightBlue)
    ){
        Icon(
            imageVector = if (type == "Terbuka") Icons.Default.Login else Icons.Default.Logout,
            contentDescription = if (type == "Terbuka") "Access Out" else "Access In",
            tint = LightGray,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.size(10.dp))
        Column (
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = toolsName,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(Modifier.size(6.dp))
            Text(
                text = userName,
                fontWeight = FontWeight.Light,
                fontSize = 11.sp,
                color = LightGray
            )
        }
        Spacer(Modifier.size(10.dp))
        Text(
            text = date,
            fontWeight = FontWeight.Light,
            fontSize = 11.sp,
            color = LightGray
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun HistoricalAccessItemPreview() {
    Box (
        modifier = Modifier
            .background(LightGray)
            .padding(16.dp)
    ) {
        HistoricalAccessItem(
            toolsName = "Nama",
            userName = "User",
            type = "Terbuka",
            date = "10/10/2023, 10.30"
        )
    }
}