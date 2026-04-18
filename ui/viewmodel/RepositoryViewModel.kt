package com.app.modules.module4.pr3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.modules.module4.pr3.domain.model.RepositoryModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryViewModel : ViewModel() {
    private var repositoryList: List<RepositoryModel> = emptyList()

    private val _searchResults = MutableStateFlow(repositoryList)
    val searchResults = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setData(data: List<RepositoryModel>) {
        repositoryList = data
        _searchResults.value = data
    }

    fun search(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000)
            _searchResults.value = if (query.isEmpty()) {
                repositoryList
            } else {
                repositoryList.filter {
                    it.full_name.contains(query, ignoreCase = true)
                }
            }
            _isLoading.value = false
        }
    }
}