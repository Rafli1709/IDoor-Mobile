package com.example.idoor.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.idoor.R
import com.example.idoor.screen.admin.AdditionalViewModal
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun TopBarMain(
    modifier: Modifier = Modifier,
    isLogout: Boolean = false
) {
    val viewModal: AdditionalViewModal = hiltViewModel()
    var showDropdown by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.hai_message),
                fontWeight = FontWeight.Light,
                fontSize = 11.sp,
                color = LightGray
            )
            Text(
                text = viewModal.userName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = LightGray
            )
        }
        Box {
            val trailingIconModifier =
                if (isLogout) Modifier.clickable { showDropdown = true } else Modifier
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .size(40.dp)
                    .then(trailingIconModifier)
            )

            if (isLogout) {
                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = {
                        showDropdown = false
                    },
                    offset = DpOffset(x = 0.dp, y = 5.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(id = R.string.logout),
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                            )
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                        },
                        onClick = {
                            showDropdown = false
                            viewModal.logout()
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun TopBarMainPreview() {
    Box(
        modifier = Modifier
            .background(MidnightBlue)
            .padding(horizontal = 40.dp)
    ) {
        TopBarMain()
    }
}