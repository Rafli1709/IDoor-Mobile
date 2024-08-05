package com.example.idoor.screen.admin.managetools

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.idoor.R
import com.example.idoor.components.ButtonIcon
import com.example.idoor.components.DataItem
import com.example.idoor.components.ModalDetail
import com.example.idoor.components.ModalItem
import com.example.idoor.components.RetrySection
import com.example.idoor.components.SearchInput
import com.example.idoor.components.TopBarMain
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.delay

@Composable
fun ManageToolScreen(
    navController: NavController,
    viewModel: ManageToolViewModel,
    modifier: Modifier = Modifier
) {
    val entries by remember { viewModel.toolList }
    val toolInfo by remember { viewModel.toolInfo }

    var query by remember { mutableStateOf("") }
    val isDialogShown by remember { viewModel.isDialogShown }

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    val isLoadingModal by remember { viewModel.isLoadingModal }
    val errorMessageModal by remember { viewModel.errorMessageModal }

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
            TopBarMain(isLogout = true)
        }

        item {
            Text(
                text = stringResource(id = R.string.title_manage_tools),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = LightGray
            )
            Spacer(Modifier.size(10.dp))
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ButtonIcon(
                    icon = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.ic_add),
                    onClick = {
                        navController.navigate(Screen.AddTool.route)
                    },
                )
                Spacer(Modifier.size(10.dp))
                SearchInput(
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.searchToolList(it)
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
                            viewModel.getToolList()
                        }
                    )
                }
            }
        } else {
            items(entries) { entry ->
                DataItem(
                    title = entry.toolName,
                    subtitle = entry.emailUser,
                    modifier = Modifier
                        .clickable {
                            viewModel.getToolInfo(entry.id)
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(1f)
        ) {
            if (isLoadingModal) {
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
            } else if (errorMessageModal.isNotEmpty()) {
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
                            viewModel.getToolInfo(toolInfo!!.id)
                        }
                    )
                }
            } else {
                ModalDetail(
                    title = "Detail User",
                    content = {
                        ModalItem(attribute = stringResource(id = R.string.input_name), value = toolInfo!!.toolName)
                        Spacer(modifier = Modifier.size(5.dp))
                        ModalItem(attribute = stringResource(id = R.string.input_email_user), value = toolInfo!!.emailUser)
                        Spacer(modifier = Modifier.size(5.dp))
                        ModalItem(attribute = "Kode Alat", value = toolInfo!!.imei)
                        Spacer(modifier = Modifier.size(5.dp))
                        ModalItem(attribute = "Password", value = toolInfo!!.password)
                    },
                    onDismiss = {
                        viewModel.onDismissDialog()
                    },
                    onUpdate = {
                        viewModel.onDismissDialog()
                        navController.navigate(Screen.UpdateTool.route.replace("{id}", toolInfo!!.id.toString()))
                    },
                    onDelete = {
                        viewModel.onDismissDialog()
                        viewModel.deleteTool(id = toolInfo!!.id)
                    }
                )
            }
        }
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
                        viewModel.deleteTool(id = toolInfo!!.id)
                    }
                )
            }
        }
    } else if (successMessageAction.isNotEmpty()) {
        var showBox by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(1500L)
            showBox = false
            viewModel.getToolList()
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