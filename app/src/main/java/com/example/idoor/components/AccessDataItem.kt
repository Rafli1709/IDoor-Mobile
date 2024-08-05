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
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun AccessDataItem(
    name: String,
    status: String,
    modifier: Modifier = Modifier,
    date: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .background(MidnightBlue)
    ) {
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = LightGray
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = status,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                color = LightGray,
                modifier = Modifier
                    .clip(RoundedCornerShape(205.dp))
                    .background(if (status == "Master") SteelBlue else CharcoalGray)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 14.dp
                    )
            )
        }
        if (date != null) {
            Text(
                text = date,
                fontWeight = FontWeight.Light,
                fontSize = 11.sp,
                color = LightGray,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 6.dp)
                    .align(Alignment.Bottom)
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun AccessDataItemPreview() {
    Box(
        modifier = Modifier
            .background(LightGray)
            .padding(16.dp)
    ) {
        AccessDataItem(
            name = "Alat",
            status = "Master",
            date = "10 September 2023, 10:30"
        )
    }
}