package com.mustafacol.spacex.ui.launch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacol.spacex.LaunchListQuery
import com.mustafacol.spacex.data.LaunchItem
import com.mustafacol.spacex.data.NetworkResult
import com.mustafacol.spacex.repository.SpacexRepository
import com.mustafacol.spacex.repository.SpacexRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LaunchListViewModel(
    private val spacexRepository: SpacexRepository
) : ViewModel() {


    private val _launchesState = MutableStateFlow<LaunchesViewState>(
        value = LaunchesViewState.Loading
    )
    val launchesState = _launchesState.asStateFlow()

    fun getLaunches() {
        viewModelScope.launch {
            spacexRepository.getLaunches(
                0
            ).catch {
                _launchesState.value = LaunchesViewState.Error(it.message.toString())
            }.collect {
                it as NetworkResult.Success
                _launchesState.value = LaunchesViewState.Success(it.data)
            }
        }
    }

    sealed class LaunchesViewState {
        class Success(val launches: List<LaunchItem>) : LaunchesViewState()
        object Loading : LaunchesViewState()
        class Error(val errorMessage: String) : LaunchesViewState()
    }
}