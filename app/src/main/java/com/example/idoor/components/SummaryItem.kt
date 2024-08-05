package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun SummaryItem(
    icon: ImageVector,
    text: String,
    count: String,
    isLoading: Boolean,
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(LightGray)
            .padding(20.dp)
    ) {
        if (isLoading) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
            ){
                CircularProgressIndicator(color = MidnightBlue)
            }
        } else if (errorMessage.isNotEmpty()) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                RetrySection(
                    error = errorMessage,
                    onRetry = onRetry,
                    textColor = MidnightBlue
                )
            }
        } else {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MidnightBlue,
                modifier = Modifier
                    .size(24.dp)
            )
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = count,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = MidnightBlue
                )
                Text(
                    text = text,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = MidnightBlue
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun SummaryItemPreview() {
    Box (
        modifier = Modifier
            .background(MidnightBlue)
            .padding(16.dp)
    ) {
        SummaryItem(
            icon = Icons.Default.Person,
            text = "User",
            count = "10",
            isLoading = false,
            errorMessage = "",
            onRetry = {},
            modifier = Modifier
        )
    }
}