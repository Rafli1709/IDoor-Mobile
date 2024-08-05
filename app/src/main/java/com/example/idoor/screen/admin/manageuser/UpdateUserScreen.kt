package com.example.idoor.screen.admin.manageuser

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.idoor.R
import com.example.idoor.components.ButtonText
import com.example.idoor.components.OutlinedInput
import com.example.idoor.components.RetrySection
import com.example.idoor.components.TopBarAuxiliary
import com.example.idoor.data.models.users.UpdateUserEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun UpdateUserScreen(
    navController: NavController,
    viewModel: UpdateUserViewModel
) {
    val userInfo by remember { viewModel.userInfo }

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    val isLoadingModal by remember { viewModel.isLoadingModal }
    val errorMessageModal by remember { viewModel.errorMessageModal }
    val successMessageModal by remember { viewModel.successMessageModal }

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
            .padding(bottom = 40.dp)
    ) {
        TopBarAuxiliary(
            title = stringResource(id = R.string.title_update_user),
            onBack = { navController.navigateUp() }
        )
        Spacer(Modifier.size(20.dp))
        if (isLoading) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CircularProgressIndicator(color = LightGray)
            }
        } else if (errorMessage.isNotEmpty()) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                RetrySection(
                    error = errorMessage,
                    onRetry = {
                        viewModel.getUserInfo(viewModel.id!!)
                    }
                )
            }
        } else {
            LaunchedEffect(userInfo) {
                name = userInfo!!.name
                username = userInfo!!.username
                email = userInfo!!.email
            }

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
                Spacer(Modifier.size(20.dp))
                ButtonText(
                    text = stringResource(id = R.string.btn_submit),
                    onClick = {
                        viewModel.updateUser(
                            id = viewModel.id!!,
                            UpdateUserEntry(
                                name = name,
                                username = username,
                                email = email
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

    if (isLoadingModal) {
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
    } else if (errorMessageModal.isNotEmpty()) {
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
                    error = errorMessageModal,
                    onRetry = {
                        viewModel.updateUser(
                            id = viewModel.id!!,
                            UpdateUserEntry(
                                name = name,
                                username = username,
                                email = email
                            )
                        )
                    }
                )
            }
        }
    } else if (successMessageModal.isNotEmpty()) {
        var showBox by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(1500L)
            showBox = false
            navController.popBackStack()
            navController.navigate(Screen.ManageUsers.route)
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
                        text = successMessageModal,
                        fontSize = 14.sp,
                        color = LightGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}