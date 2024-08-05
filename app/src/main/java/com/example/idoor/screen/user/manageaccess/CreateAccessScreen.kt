package com.example.idoor.screen.user.manageaccess

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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.idoor.components.DropdownInput
import com.example.idoor.components.OutlinedInput
import com.example.idoor.components.RetrySection
import com.example.idoor.components.TopBarAuxiliary
import com.example.idoor.data.models.accessright.CreateAccessRight
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun CreateAccessScreen(
    navController: NavController,
    viewModel: CreateAccessViewModel
) {
    val id = viewModel.id

    val entries by remember { viewModel.userList }
    val isLoadingDropdown by remember { viewModel.isLoadingDropdown }
    val errorMessageDropdown by remember { viewModel.errorMessageDropdown }

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }
    val successMessage by remember { viewModel.successMessage }

    var query by remember { mutableStateOf("") }
    var userId by remember { mutableIntStateOf(0) }
    var timeLimit by remember { mutableStateOf("") }

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
            title = "Tambah Hak Akses",
            onBack = {
                navController.popBackStack()
                navController.navigate(Screen.ManageAccess.route.replace("{id}", id.toString()))
            }
        )
        Spacer(Modifier.size(20.dp))
        Column {
            DropdownInput(
                query = query,
                label = "User",
                placeholder = "Pilih user...",
                leadingIcon = Icons.Default.Person,
                isLoading = isLoadingDropdown,
                errorMessage = errorMessageDropdown,
                onRetry = {
                    viewModel.getUserList()
                },
                onSearch = { newQuery ->
                    query = newQuery
                    viewModel.searchUserList(query)
                },
                onValueChange = { newValue ->
                    userId = newValue
                },
                lists = entries
            )
            Spacer(Modifier.size(20.dp))
            ButtonText(
                text = stringResource(id = R.string.btn_submit),
                onClick = {
                    viewModel.createAccessRight(
                        CreateAccessRight(
                            userId = userId,
                            alatId = id!!.toInt(),
                            timeLimit = timeLimit.ifEmpty { null }
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
                        viewModel.createAccessRight(
                            CreateAccessRight(
                                userId = userId,
                                alatId = id!!.toInt(),
                                timeLimit = timeLimit.ifEmpty { null }
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
            navController.navigate(Screen.ManageAccess.route.replace("{id}", id.toString()))
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