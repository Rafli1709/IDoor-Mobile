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
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.idoor.data.models.users.CreateUserEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun CreateUserScreen(
    navController: NavController,
    viewModel: CreateUserViewModel
) {
    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }
    val successMessage by remember { viewModel.successMessage }

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationPassword by remember { mutableStateOf("") }
    var visualTransformationPassword by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }
    var trailingIconPassword by remember { mutableStateOf(Icons.Default.Visibility) }
    var visualTransformationConfirmationPassword by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }
    var trailingIconConfirmationPassword by remember { mutableStateOf(Icons.Default.Visibility) }

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
            title = stringResource(id = R.string.title_create_user),
            onBack = { navController.navigateUp() }
        )
        Spacer(Modifier.size(20.dp))
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
                value = password,
                label = stringResource(id = R.string.input_password),
                visualTransformation = visualTransformationPassword,
                onValueChange = {
                    password = it
                },
                leadingIcon = Icons.Default.Password,
                trailingIcon = trailingIconPassword,
                clickableTrailingIcon = true,
                onTrailingIconClick = {
                    if (trailingIconPassword == Icons.Default.Visibility) {
                        visualTransformationPassword = VisualTransformation.None
                        trailingIconPassword = Icons.Default.VisibilityOff
                    } else {
                        visualTransformationPassword = PasswordVisualTransformation()
                        trailingIconPassword = Icons.Default.Visibility
                    }
                }
            )
            Spacer(Modifier.size(10.dp))
            OutlinedInput(
                value = confirmationPassword,
                label = stringResource(id = R.string.input_confirmation_password),
                visualTransformation = visualTransformationConfirmationPassword,
                onValueChange = {
                    confirmationPassword = it
                },
                leadingIcon = Icons.Default.Password,
                trailingIcon = trailingIconConfirmationPassword,
                clickableTrailingIcon = true,
                onTrailingIconClick = {
                    if (trailingIconConfirmationPassword == Icons.Default.Visibility) {
                        visualTransformationConfirmationPassword = VisualTransformation.None
                        trailingIconConfirmationPassword = Icons.Default.VisibilityOff
                    } else {
                        visualTransformationConfirmationPassword = PasswordVisualTransformation()
                        trailingIconConfirmationPassword = Icons.Default.Visibility
                    }
                }
            )
            Spacer(Modifier.size(20.dp))
            ButtonText(
                text = stringResource(id = R.string.btn_submit),
                onClick = {
                    viewModel.createUser(
                        CreateUserEntry(
                            name = name,
                            username = username,
                            email = email,
                            password = password,
                            confirmationPassword = confirmationPassword,
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(50.dp)
            )
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
                        viewModel.createUser(
                            CreateUserEntry(
                                name = name,
                                username = username,
                                email = email,
                                password = password,
                                confirmationPassword = confirmationPassword,
                            )
                        )
                    }
                )
            }
        }
    } else if (successMessage.isNotEmpty()) {
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
                        text = successMessage,
                        fontSize = 14.sp,
                        color = LightGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
