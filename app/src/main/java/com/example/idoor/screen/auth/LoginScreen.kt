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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.idoor.R
import com.example.idoor.components.ButtonText
import com.example.idoor.components.OutlinedInput
import com.example.idoor.components.RetrySection
import com.example.idoor.data.models.auth.LoginEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visualTransformation by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }
    var trailingIcon by remember { mutableStateOf(Icons.Default.Visibility) }

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
                TitleLogin()
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Spacer(Modifier.size(60.dp))
                Column {
                    OutlinedInput(
                        value = email,
                        label = stringResource(id = R.string.input_email),
                        placeholder = stringResource(id = R.string.placeholder_email),
                        leadingIcon = Icons.Default.Email,
                        onValueChange = {
                            email = it
                        }
                    )
                    Spacer(Modifier.size(10.dp))
                    OutlinedInput(
                        value = password,
                        label = "Password",
                        visualTransformation = visualTransformation,
                        onValueChange = {
                            password = it
                        },
                        leadingIcon = Icons.Default.Password,
                        trailingIcon = trailingIcon,
                        clickableTrailingIcon = true,
                        onTrailingIconClick = {
                            if (trailingIcon == Icons.Default.Visibility) {
                                visualTransformation = VisualTransformation.None
                                trailingIcon = Icons.Default.VisibilityOff
                            } else {
                                visualTransformation = PasswordVisualTransformation()
                                trailingIcon = Icons.Default.Visibility
                            }
                        }
                    )
                    Spacer(Modifier.size(20.dp))
                    ButtonText(
                        text = stringResource(id = R.string.login),
                        onClick = {
                            viewModel.login(
                                LoginEntry(
                                    email = email,
                                    password = password
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
                    text = stringResource(id = R.string.question_register),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = LightGray
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = stringResource(id = R.string.register),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = LightGray,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(Screen.Register.route)
                        }
                )
            }
        }
    }
}

@Composable
fun TitleLogin(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.title_login),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = LightGray
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = stringResource(id = R.string.subtitle_login),
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = LightGray
        )
    }
}