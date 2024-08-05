package com.example.idoor.screen

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.idoor.R
import com.example.idoor.components.ButtonText
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    viewModel: OnBoardingViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
            .padding(bottom = 20.dp, top = 15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.desc_logo),
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 38.sp,
                color = LightGray
            )
        }
        Image(
            painter = painterResource(id = R.drawable.img_main),
            contentDescription = stringResource(id = R.string.desc_main),
            modifier = Modifier
                .fillMaxHeight(fraction = 0.4f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .navigationBarsPadding()
        ) {
            Text(
                text = stringResource(id = R.string.tagline),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = LightGray,
                lineHeight = 30.sp
            )
            Spacer(Modifier.size(30.dp))
            ButtonText(
                text = stringResource(id = R.string.btn_main),
                onClick = {
                    viewModel.saveStartDestination()
                },
                modifier = Modifier.height(52.dp)
            )
            Spacer(Modifier.size(30.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.question_login),
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
                            navController.navigate(Screen.Register.route)
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL, apiLevel = Build.VERSION_CODES.O)
@Composable
fun OnBoardingScreenPreview() {
    val navController = rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()
    OnBoardingScreen(navController = navController, viewModel = viewModel)
}