package com.mustafacol.spacex.ui.launch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.data.NetworkResult
import com.mustafacol.spacex.repository.SpacexRepository
import com.mustafacol.spacex.repository.SpacexRepositoryImpl
import com.mustafacol.spacex.utils.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LaunchListViewModel(
    private val spacexRepository: SpacexRepository
) : ViewModel() {


    private val _launchesState =
        MutableStateFlow<LaunchesViewState>(value = LaunchesViewState.Loading)
    val launchesState = _launchesState.asStateFlow()
    private val _currentPageState = MutableStateFlow(0)
    private val _isLastPage = MutableStateFlow(false)

    fun getLaunches() {
        viewModelScope.launch {
            if (_isLastPage.value) return@launch
            spacexRepository.getLaunches(
                _currentPageState.value * Constant.PAGE_LIMIT
            ).collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _launchesState.value =
                            LaunchesViewState.Success(it.data)
                        if (it.data.size < Constant.PAGE_LIMIT)
                            _isLastPage.emit(true)
                    }
                    is NetworkResult.Failure -> LaunchesViewState.Error(it.throwable.message.toString())
                }
            }
        }
    }

    fun getNextLaunches() {
        _launchesState.value = LaunchesViewState.Loading
        _currentPageState.value = _currentPageState.value + 1
        getLaunches()
    }

    sealed class LaunchesViewState {
        class Success(val launches: List<LaunchItem>) : LaunchesViewState()
        object Loading : LaunchesViewState()
        class Error(val errorMessage: String) : LaunchesViewState()
    }
}