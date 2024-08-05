package com.example.idoor.screen.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.idoor.R
import com.example.idoor.components.ButtonIconText
import com.example.idoor.components.ButtonText
import com.example.idoor.components.ModalItem
import com.example.idoor.components.OutlinedInput
import com.example.idoor.components.RetrySection
import com.example.idoor.components.TopBarAuxiliary
import com.example.idoor.components.TopBarMain
import com.example.idoor.data.models.users.UpdateProfileEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun UpdateProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val authData by remember { viewModel.authData }

    val isLoading by remember { viewModel.isLoading }
    val isSuccess by remember { viewModel.isSuccess }
    val errorMessage by remember { viewModel.errorMessage }

    var name by remember { mutableStateOf(authData?.user!!.name) }
    var username by remember { mutableStateOf(authData?.user!!.username) }
    var email by remember { mutableStateOf(authData?.user!!.email) }
    var phoneNumber by remember { mutableStateOf(authData?.user!!.phoneNumber) }
    var gender by remember { mutableStateOf(authData?.user!!.gender) }
    var dateBirth by remember { mutableStateOf(authData?.user!!.dateBirth) }
    var address by remember { mutableStateOf(authData?.user!!.address) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
    ) {
        item {
            TopBarAuxiliary(
                title = "Update Profile",
                onBack = { navController.navigateUp() }
            )
            Spacer(Modifier.size(30.dp))
        }

        item {
            Column {
                OutlinedInput(
                    value = name,
                    label = stringResource(id = R.string.input_name),
                    leadingIcon = Icons.Default.Person,
                    onValueChange = { newInput ->
                        name = newInput
                    }
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = username,
                    label = stringResource(id = R.string.input_username),
                    leadingIcon = Icons.Default.Person,
                    onValueChange = { newInput ->
                        username = newInput
                    }
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = email,
                    label = stringResource(id = R.string.input_email),
                    placeholder = stringResource(id = R.string.placeholder_email),
                    leadingIcon = Icons.Default.Email,
                    onValueChange = { newInput ->
                        email = newInput
                    }
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = phoneNumber,
                    label = "Nomor HP",
                    placeholder = "08xxxxxxxxxx",
                    leadingIcon = Icons.Default.Phone,
                    onValueChange = { newInput ->
                        phoneNumber = newInput
                    }
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = gender,
                    label = "Jenis Kelamin",
                    leadingIcon = Icons.Default.Man,
                    onValueChange = { newInput ->
                        gender = newInput
                    }
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = dateBirth,
                    label = "Tanggal Lahir",
                    placeholder = "DD/MM/YYYY",
                    leadingIcon = Icons.Default.CalendarMonth,
                    onValueChange = { newInput ->
                        dateBirth = newInput
                    }
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = address,
                    label = "Alamat",
                    leadingIcon = Icons.Default.Place,
                    onValueChange = { newInput ->
                        address = newInput
                    }
                )
                Spacer(Modifier.size(20.dp))
                ButtonText(
                    text = stringResource(id = R.string.btn_submit),
                    onClick = {
                        viewModel.updateUser(
                            UpdateProfileEntry(
                                name = name,
                                username = username,
                                email = email,
                                phoneNumber = phoneNumber,
                                gender = gender,
                                dateBirth = dateBirth,
                                address = address
                            )
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(50.dp)
                )
            }
        }
    }

    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.dp, LightGray, shape = RoundedCornerShape(15.dp))
                    .background(MidnightBlue)
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                CircularProgressIndicator(color = LightGray)
            }
        }
    } else if (errorMessage.isNotEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(1.dp, LightGray, shape = RoundedCornerShape(15.dp))
                    .background(MidnightBlue)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                RetrySection(
                    error = errorMessage,
                    onRetry = {
                        viewModel.updateUser(
                            UpdateProfileEntry(
                                name = name,
                                username = username,
                                email = email,
                                phoneNumber = phoneNumber,
                                gender = gender,
                                dateBirth = dateBirth,
                                address = address
                            )
                        )
                    }
                )
            }
        }
    } else if (isSuccess) {
        var showBox by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(1500L)
            showBox = false
            navController.popBackStack()
            navController.navigate(Screen.Profile.route)
        }

        if (showBox) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .zIndex(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, LightGray, shape = RoundedCornerShape(15.dp))
                        .background(MidnightBlue)
                        .fillMaxWidth()
                        .padding(40.dp)
                ) {
                    Text(
                        text = "Berhasil mengubah data profile user",
                        fontSize = 14.sp,
                        color = LightGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}