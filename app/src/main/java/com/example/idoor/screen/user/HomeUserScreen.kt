package com.example.idoor.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import com.example.idoor.R
import com.example.idoor.components.HistoricalAccessItem
import com.example.idoor.components.RetrySection
import com.example.idoor.components.SummaryItem
import com.example.idoor.components.TopBarMain
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun HomeUserScreen(
    viewModel: HomeUserViewModel,
    modifier: Modifier = Modifier
) {
    val entries by remember { viewModel.historyList }

    val ownerCount by remember { viewModel.ownerCount }
    val borrowCount by remember { viewModel.borrowCount }

    val isLoadingSummary by remember { viewModel.isLoadingSummary }
    val errorMessageSummary by remember { viewModel.errorMessageSummary }

    val isLoadingHistory by remember { viewModel.isLoadingHistory }
    val errorMessageHistory by remember { viewModel.errorMessageHistory }

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
    ){
        item {
            TopBarMain()
        }

        item {
            Text(
                text = stringResource(id = R.string.title_sum_user),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = LightGray
            )
            Spacer(Modifier.size(10.dp))
        }

        item {
            Row {
                    SummaryItem(
                        icon = Icons.Default.Construction,
                        text = stringResource(id = R.string.sum_owner),
                        count = ownerCount.toString(),
                        isLoading = isLoadingSummary,
                        errorMessage = errorMessageSummary,
                        onRetry = {
                            viewModel.getSummary()
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(Modifier.size(16.dp))
                    SummaryItem(
                        icon = Icons.Default.Construction,
                        text = stringResource(id = R.string.sum_borrowed),
                        count = borrowCount.toString(),
                        isLoading = isLoadingSummary,
                        errorMessage = errorMessageSummary,
                        onRetry = {
                            viewModel.getSummary()
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
            }
            Spacer(Modifier.size(30.dp))
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.title_history),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = LightGray
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(id = R.string.ic_forward),
                    tint = LightGray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {}
                )
            }
            Spacer(Modifier.size(16.dp))
        }

        if (isLoadingHistory) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                ) {
                    CircularProgressIndicator(
                        color = LightGray,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        } else if (errorMessageHistory.isNotEmpty()) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp),
                ) {
                    RetrySection(
                        error = errorMessageHistory,
                        onRetry = {
                            viewModel.getHistory()
                        }
                    )
                }
            }
        } else {
            items(entries) { entry ->
                HistoricalAccessItem(
                    toolsName = entry.toolName,
                    userName = entry.userName,
                    type = entry.type,
                    date = entry.accessTime,
                    modifier = Modifier.padding(vertical = 15.dp)
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = LightGray,
                    thickness = 0.5.dp
                )
            }
        }
    }
}