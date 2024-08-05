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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.R
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun DataItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row (
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .background(MidnightBlue)
    ){
        Column (
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightGray
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = subtitle,
                fontWeight = FontWeight.Light,
                fontSize = 11.sp,
                color = LightGray
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun DataItemPreview() {
    Box (
        modifier = Modifier
            .background(LightGray)
            .padding(16.dp)
    ) {
        DataItem(
            title = "User",
            subtitle = "Email",
        )
    }
}