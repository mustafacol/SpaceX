package com.mustafacol.spacex.ui.launch_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacol.spacex.data.LaunchDetailsItem
import com.mustafacol.spacex.data.NetworkResult
import com.mustafacol.spacex.repository.SpacexRepository
import com.mustafacol.spacex.ui.launch_list.LaunchListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(
    private val spacexRepository: SpacexRepository
) : ViewModel() {

    private val _launchDetailsState = MutableStateFlow<LaunchDetailsViewState>(
        value = LaunchDetailsViewState.Loading
    )
    val launchDetailsViewState = _launchDetailsState.asStateFlow()

    fun getLaunchDetails(launchId: String) {
        viewModelScope.launch {
            spacexRepository.getLaunchDetails(launchId)
                .collect {
                    when (it) {
                        is NetworkResult.Success -> _launchDetailsState.value =
                            LaunchDetailsViewState.Success(it.data)
                        is NetworkResult.Failure -> LaunchDetailsViewState.Error(it.throwable.message.toString())

                    }
                }
        }
    }


    sealed class LaunchDetailsViewState {
        class Success(val launchDetailsItem: LaunchDetailsItem) : LaunchDetailsViewState()
        object Loading : LaunchDetailsViewState()
        class Error(val errorMessage: String) : LaunchDetailsViewState()
    }
}
