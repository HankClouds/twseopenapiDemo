package com.hung.myapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hung.myapplication.data.MainDataRepository
import com.hung.myapplication.data.MainStockDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainDataRepository: MainDataRepository) :
    ViewModel() {

    private val _data = MutableStateFlow<List<MainStockDataModel>>(emptyList())
    val data: StateFlow<List<MainStockDataModel>> = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    sealed interface UiEvent {
        data object LoadData : UiEvent
    }

    init {
        fetchDataAndSortDesc()
//        viewModelScope.launch {
//            uiEvent.collect { event ->
//                when (event) {
//                    UiEvent.LoadData -> fetchDataAndSortDesc()
//                }
//            }
//        }
//
//        viewModelScope.launch {
//            _uiEvent.emit(UiEvent.LoadData)
//        }
    }

    private fun fetchDataAndSortDesc() {
        _isLoading.value = true
        viewModelScope.launch {
            mainDataRepository.fetchStockData()
                .collectLatest { result ->
                    _isLoading.update { false }
                    result.onSuccess { dataList ->
                        _data.update {
                            dataList.sortedByDescending { it.Code }
                        }
                    }
                    result.onFailure { e ->
                        _error.update { e.message }
                    }
                }
        }
    }

    fun sortDataByDesc() {
        _data.update { currentList ->
            currentList.sortedByDescending { it.Code }
        }
    }

    fun sortDataByAsc() {
        _data.update { currentList ->
            currentList.sortedBy { it.Code }
        }
    }

}