package com.example.idoor.screen.admin.managetools

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
import androidx.compose.material.icons.filled.Construction
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
import com.example.idoor.data.models.tools.CreateToolEntry
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun UpdateToolScreen(
    navController: NavController,
    viewModel: UpdateToolViewModel,
    modifier: Modifier = Modifier
) {
    val entries by remember { viewModel.userList }
    val isLoadingDropdown by remember { viewModel.isLoading }
    val errorMessageDropdown by remember { viewModel.errorMessage }

    val toolInfo by remember { viewModel.toolInfo }

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    val isLoadingModal by remember { viewModel.isLoadingModal }
    val errorMessageModal by remember { viewModel.errorMessageModal }
    val successMessageModal by remember { viewModel.successMessageModal }

    var query by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var userId by remember { mutableIntStateOf(0) }
    var selectedUser by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
            .padding(bottom = 40.dp)
    ) {
        TopBarAuxiliary(
            title = stringResource(id = R.string.title_update_tools),
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
                        viewModel.getToolInfo(viewModel.id!!)
                    }
                )
            }
        } else {
            LaunchedEffect(toolInfo) {
                selectedUser = toolInfo!!.emailUser
                name = toolInfo!!.toolName
            }

            Column {
                DropdownInput(
                    query = query,
                    label = "User",
                    placeholder = "Pilih user...",
                    leadingIcon = Icons.Default.Construction,
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
                    lists = entries,
                    selectedItem = selectedUser
                )
                Spacer(Modifier.size(10.dp))
                OutlinedInput(
                    value = name,
                    label = stringResource(id = R.string.input_tool_name),
                    leadingIcon = Icons.Default.Person,
                    onValueChange = { newInput ->
                        name = newInput
                    }
                )
                Spacer(Modifier.size(20.dp))
                ButtonText(
                    text = stringResource(id = R.string.btn_submit),
                    onClick = {
                        viewModel.updateTool(
                            id = viewModel.id!!,
                            createToolEntry = CreateToolEntry(
                                userId = userId,
                                name = name,
                                plaintext = "",
                                imei = ""
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
                        viewModel.updateTool(
                            id = viewModel.id!!,
                            createToolEntry = CreateToolEntry(
                                userId = userId,
                                name = name,
                                plaintext = "",
                                imei = ""
                            )
                        )
                    }
                )
            }
        }
    } else if (successMessageModal.isNotEmpty()) {
        var showBox by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.Main) {
                delay(1500L)
                showBox = false
                navController.popBackStack()
                navController.navigate(Screen.ManageTools.route)
            }
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