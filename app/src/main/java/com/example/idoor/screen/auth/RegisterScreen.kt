package com.example.idoor.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.idoor.R
import com.example.idoor.components.ButtonText
import com.example.idoor.components.OutlinedInput
import com.example.idoor.components.RetrySection
import com.example.idoor.data.models.auth.LoginEntry
import com.example.idoor.data.models.auth.RegisterEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.IDoorTheme
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

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

    if (isLoading) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightBlue)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            CircularProgressIndicator(color = LightGray)
        }
    } else if (errorMessage.isNotEmpty()) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightBlue)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            RetrySection(
                error = errorMessage,
                onRetry = {
                    viewModel.login(
                        LoginEntry(
                            email = email,
                            password = password
                        )
                    )
                }
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(MidnightBlue)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 40.dp)
                .padding(bottom = 40.dp, top = 30.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .size(60.dp)
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = LightGray
                    )
                }
                Spacer(Modifier.size(20.dp))
                TitleRegister()
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Spacer(Modifier.size(40.dp))
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
                        text = stringResource(id = R.string.register),
                        onClick = {
                            viewModel.register(
                                RegisterEntry(
                                    name = name,
                                    username = username,
                                    email = email,
                                    password = password,
                                    confirmationPassword = confirmationPassword
                                )
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .height(50.dp)
                    )
                }
            }
            Row {
                Text(
                    text = stringResource(id = R.string.question_login),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = LightGray
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = stringResource(id = R.string.login),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = LightGray,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(Screen.Login.route)
                        }
                )
            }
        }
    }
}

@Composable
fun TitleRegister(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.title_register),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = LightGray
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = stringResource(id = R.string.subtitle_register),
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = LightGray
        )
    }
}
