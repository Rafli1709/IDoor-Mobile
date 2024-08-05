package com.example.idoor.screen.user.manageaccess

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import com.example.idoor.components.ButtonIcon
import com.example.idoor.components.DetailAccessDataItem
import com.example.idoor.components.ModalDetail
import com.example.idoor.components.ModalItem
import com.example.idoor.components.RetrySection
import com.example.idoor.components.SearchInput
import com.example.idoor.components.TopBarAuxiliary
import com.example.idoor.data.models.accessright.DetailAccessRightEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun ManageAccessScreen(
    navController: NavController,
    viewModel: ManageAccessViewModel,
    modifier: Modifier = Modifier
) {
    val id = viewModel.id

    val entries by remember { viewModel.accessRightList }
    var accessRightInfo by remember { mutableStateOf<DetailAccessRightEntry?>(null) }

    var query by remember { mutableStateOf("") }
    val isDialogShown by remember { viewModel.isDialogShown }

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    val isLoadingAction by remember { viewModel.isLoadingAction }
    val errorMessageAction by remember { viewModel.errorMessageAction }
    val successMessageAction by remember { viewModel.successMessageAction }

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
                title = "Kelola Hak Akses Alat",
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        item {
            Spacer(Modifier.size(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ButtonIcon(
                    icon = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.ic_add),
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(
                            Screen.AddAccess.route.replace(
                                "{id}",
                                id.toString()
                            )
                        )
                    },
                )
                Spacer(Modifier.size(10.dp))
                SearchInput(
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.searchAccessRightList(it)
                    },
                )
            }
        }

        if (isLoading) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(300.dp)
                ) {
                    CircularProgressIndicator(
                        color = LightGray,
                        modifier = Modifier.size(80.dp)
                    )
                }
                Spacer(Modifier.size(5.dp))
            }
        } else if (errorMessage.isNotEmpty()) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(450.dp),
                ) {
                    RetrySection(
                        error = errorMessage,
                        onRetry = {
                            viewModel.getAccessRightList()
                        }
                    )
                }
            }
        } else {
            items(entries) { entry ->
                DetailAccessDataItem(
                    userName = entry.userName,
                    email = entry.email,
                    date = entry.timeLimit,
                    onDelete = {
                        viewModel.deleteAccessRight(entry.id)
                    },
                    modifier = Modifier
                        .clickable {
                            accessRightInfo = DetailAccessRightEntry(
                                id = entry.id,
                                email = entry.email,
                                userName = entry.userName,
                                timeLimit = entry.timeLimit
                            )
                            viewModel.onDetailClick()
                        }
                        .padding(vertical = 15.dp)
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = LightGray,
                    thickness = 0.5.dp
                )
            }
        }
    }

    if (isDialogShown) {
        ModalDetail(
            title = "Detail User",
            content = {
                ModalItem(
                    attribute = "Nama Alat",
                    value = accessRightInfo!!.email
                )
                Spacer(modifier = Modifier.size(5.dp))
                ModalItem(
                    attribute = "Nama User Peminjam",
                    value = accessRightInfo!!.userName
                )
                accessRightInfo!!.timeLimit?.let {
                    Spacer(modifier = Modifier.size(5.dp))
                    ModalItem(
                        attribute = "Batas Waktu Akses",
                        value = it
                    )
                }
            },
            onDismiss = {
                viewModel.onDismissDialog()
            },
            isUpdate = false,
            onDelete = {
                viewModel.onDismissDialog()
                viewModel.deleteAccessRight(id = accessRightInfo!!.id)
            }
        )
    }

    if (isLoadingAction) {
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
    } else if (errorMessageAction.isNotEmpty()) {
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
                    error = errorMessageAction,
                    onRetry = {
                        viewModel.deleteAccessRight(id = accessRightInfo!!.id)
                    }
                )
            }
        }
    } else if (successMessageAction.isNotEmpty()) {
        var showBox by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(1500L)
            showBox = false
            viewModel.getAccessRightList()
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
                        text = successMessageAction,
                        fontSize = 14.sp,
                        color = LightGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}