package com.example.idoor.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idoor.R
import com.example.idoor.components.SummaryItem
import com.example.idoor.components.TopBarMain
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun HomeAdminScreen(
    viewModel: HomeAdminViewModel,
    modifier: Modifier = Modifier
) {
    val userCount by remember { viewModel.userCount }
    val toolCount by remember { viewModel.toolCount }

    val isLoadingUser by remember { viewModel.isLoadingUser }
    val errorMessageUser by remember { viewModel.errorMessageUser }

    val isLoadingTool by remember { viewModel.isLoadingTool }
    val errorMessageTool by remember { viewModel.errorMessageTool }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
    ) {
        TopBarMain(isLogout = true)
        Text(
            text = stringResource(id = R.string.title_sum_admin),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = LightGray
        )
        Spacer(Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    SummaryItem(
                        icon = Icons.Default.Person,
                        text = stringResource(id = R.string.sum_user),
                        count = userCount.toString(),
                        isLoading = isLoadingUser,
                        errorMessage = errorMessageUser,
                        onRetry = {
                            viewModel.getTotalUser()
                            viewModel.getTotalTool()
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                item {
                    SummaryItem(
                        icon = Icons.Default.Construction,
                        text = stringResource(id = R.string.sum_tools),
                        count = toolCount.toString(),
                        isLoading = isLoadingTool,
                        errorMessage = errorMessageTool,
                        onRetry = {
                            viewModel.getTotalUser()
                            viewModel.getTotalTool()
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}