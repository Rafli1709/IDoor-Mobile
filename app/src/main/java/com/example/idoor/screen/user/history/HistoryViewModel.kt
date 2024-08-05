package com.example.idoor.screen.user.history

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idoor.data.models.accesshistory.HistoryEntry
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.AccessHistoryRepository
import com.example.idoor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val accessHistoryRepository: AccessHistoryRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var userId = mutableIntStateOf(0)

    var accessHistoryList = mutableStateOf<List<HistoryEntry>>(listOf())

    private var cachedAccessHistoryList = listOf<HistoryEntry>()
    private var isSearch = true

    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    init {
        getUserId()
        getAccessRightList()
    }

    private fun getUserId() {
        viewModelScope.launch {
            dataStoreRepository.readUserId()
                .collect { id ->
                    userId.intValue = id ?: 0
                }
        }
    }

    fun searchAccessHistoryList(query: String) {
        val listToSearch = if (isSearch) {
            accessHistoryList.value
        } else {
            cachedAccessHistoryList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                accessHistoryList.value = cachedAccessHistoryList
                isSearch = true
                return@launch
            }
            val results = listToSearch.filter {
                it.toolName.contains(query.trim(), ignoreCase = true) ||
                        it.userName.contains(query.trim(), ignoreCase = true) ||
                        it.accessTime.contains(query.trim(), ignoreCase = true)
            }
            if (isSearch) {
                cachedAccessHistoryList = accessHistoryList.value
                isSearch = false
            }
            accessHistoryList.value = results
        }
    }

    fun getAccessRightList() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = accessHistoryRepository.getAccessHistories(userId.intValue)) {
                is Resource.Success -> {
                    val accessHistoryEntries = result.data?.map { entry ->
                        HistoryEntry(
                            toolName = entry.alat.nama,
                            userName = entry.user.name,
                            type = "Terbuka",
                            accessTime = entry.waktu_akses
                        )
                    }

                    errorMessage.value = ""
                    isLoading.value = false
                    accessHistoryList.value = accessHistoryEntries!!
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}