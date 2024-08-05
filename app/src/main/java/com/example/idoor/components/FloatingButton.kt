package com.example.idoor.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun FloatingButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        containerColor = SteelBlue,
        shape = CircleShape,
        onClick = onClick,
        modifier = Modifier
            .offset(y = 56.dp)
            .size(65.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Key,
            contentDescription = null,
            tint = LightGray,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun FloatingButtonPreview() {
    Box (
        modifier = Modifier.padding(16.dp)
    ) {
        FloatingButton(
            onClick = { },
        )
    }
}