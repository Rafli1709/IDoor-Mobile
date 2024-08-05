package com.example.idoor.screen.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.idoor.R
import com.example.idoor.components.ButtonIconText
import com.example.idoor.components.ModalItem
import com.example.idoor.components.TopBarMain
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val authData by remember { viewModel.authData }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
    ) {
        item {
            TopBarMain()
        }

        item {
            Text(
                text = "Profile",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = LightGray
            )
            Spacer(Modifier.size(30.dp))
        }

        item {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.size(30.dp))
        }

        item {
            ModalItem(
                attribute = stringResource(id = R.string.input_name),
                value = authData?.user!!.name,
            )
            Spacer(modifier = Modifier.size(10.dp))
            ModalItem(
                attribute = stringResource(id = R.string.input_username),
                value = authData?.user!!.username
            )
            Spacer(modifier = Modifier.size(10.dp))
            ModalItem(
                attribute = stringResource(id = R.string.input_email),
                value = authData?.user!!.email
            )
            Spacer(modifier = Modifier.size(10.dp))
            ModalItem(
                attribute = "Nomor HP",
                value = authData?.user!!.phoneNumber
            )
            Spacer(modifier = Modifier.size(10.dp))
            ModalItem(
                attribute = "Jenis Kelamin",
                value = authData?.user!!.gender
            )
            Spacer(modifier = Modifier.size(10.dp))
            ModalItem(
                attribute = "Tanggal Lahir",
                value = authData?.user!!.dateBirth
            )
            Spacer(modifier = Modifier.size(10.dp))
            ModalItem(
                attribute = "Alamat",
                value = authData?.user!!.address
            )
            Spacer(modifier = Modifier.size(30.dp))
        }

        item {
            Row {
                ButtonIconText(
                    icon = Icons.Default.Edit,
                    text = "Update Profile",
                    onClick = {
                        navController.navigate(Screen.UpdateProfile.route)
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(10.dp))
                ButtonIconText(
                    icon = Icons.Default.Logout,
                    text = "Logout",
                    background = Color.DarkGray,
                    onClick = {
                        viewModel.logout()
                    }
                )
            }
        }
    }
}