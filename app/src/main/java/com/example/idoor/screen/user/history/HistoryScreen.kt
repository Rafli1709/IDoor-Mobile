package com.example.idoor.screen.user.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idoor.components.HistoricalAccessItem
import com.example.idoor.components.RetrySection
import com.example.idoor.components.SearchInput
import com.example.idoor.components.TopBarMain
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val entries by remember { viewModel.accessHistoryList }

    var query by remember { mutableStateOf("") }

    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlue)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 40.dp)
    ) {
        item {
            TopBarMain()
        }

        item {
            Text(
                text = "History Akses",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = LightGray
            )
            Spacer(Modifier.size(30.dp))
        }

        item {
            SearchInput(
                query = query,
                onQueryChange = {
                    query = it
                    viewModel.searchAccessHistoryList(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.size(20.dp))
        }

        if (isLoading) {
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
        } else if (errorMessage.isNotEmpty()) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
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