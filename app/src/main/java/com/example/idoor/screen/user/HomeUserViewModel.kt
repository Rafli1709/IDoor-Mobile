package com.example.idoor.screen.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.accesshistory.HistoryEntry
import com.example.idoor.data.models.users.UserEntry
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.AccessHistoryRepository
import com.example.idoor.repository.AccessRightRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeUserViewModel @Inject constructor(
    private val accessRightRepository: AccessRightRepository,
    private val accessHistoryRepository: AccessHistoryRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private var userId = mutableIntStateOf(0)

    var historyList = mutableStateOf<List<HistoryEntry>>(listOf())
    var ownerCount = mutableIntStateOf(0)
    var borrowCount = mutableIntStateOf(0)

    var isLoadingSummary = mutableStateOf(false)
    var errorMessageSummary = mutableStateOf("")

    var isLoadingHistory = mutableStateOf(false)
    var errorMessageHistory = mutableStateOf("")

    init {
        getUserId()
        getSummary()
        getHistory()
    }

    private fun getUserId() {
        viewModelScope.launch {
            dataStoreRepository.readUserId()
                .collect { id ->
                    userId.intValue = id ?: 0
                }
        }
    }

    fun getSummary() {
        viewModelScope.launch {
            isLoadingSummary.value = true
            when (val result = accessRightRepository.getAccessRights(userId.intValue)) {
                is Resource.Success -> {
                    errorMessageSummary.value = ""
                    isLoadingSummary.value = false
                    ownerCount.intValue = result.data!!.totalAlatDimiliki
                    borrowCount.intValue = result.data.totalAlatDipinjam
                }

                is Resource.Error -> {
                    errorMessageSummary.value = result.message!!
                    isLoadingSummary.value = false
                }
            }
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            isLoadingHistory.value = true
            when (val result = accessHistoryRepository.getAccessHistories(userId.intValue)) {
                is Resource.Success -> {
                    val historyEntries = result.data?.take(10)?.map { entry ->
                        HistoryEntry(
                            toolName = entry.alat.nama,
                            userName = entry.user.name,
                            type = "Terbuka",
                            accessTime = entry.waktu_akses
                        )
                    }

                    errorMessageHistory.value = ""
                    isLoadingHistory.value = false
                    historyList.value = historyEntries!!
                }

                is Resource.Error -> {
                    errorMessageHistory.value = result.message!!
                    isLoadingHistory.value = false
                }
            }
        }
    }
}