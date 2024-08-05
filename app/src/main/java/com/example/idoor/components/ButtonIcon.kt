package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.idoor.R
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.SteelBlue

@Composable
fun ButtonIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    tintIcon: Color = LightGray,
    background: Color = SteelBlue,
    iconSize: Dp = 24.dp,
    containerSize: Dp = 56.dp,
) {

    IconButton(
        onClick = onClick,
        enabled = enabled,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tintIcon,
                modifier = Modifier.size(iconSize)
            )
        },
        modifier = modifier
            .clip(shape)
            .background(background)
            .size(containerSize)
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun ButtonIconPreview() {
    Box (
        modifier = Modifier.padding(16.dp)
    ) {
        ButtonIcon(
            icon = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.ic_add),
            onClick = { }
        )
    }
}