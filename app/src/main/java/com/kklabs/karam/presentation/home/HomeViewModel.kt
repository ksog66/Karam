package com.kklabs.karam.presentation.home

import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.response.HomeDataResponse
import com.kklabs.karam.data.repo.HomeRepository
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<HomeFeedUiState>(HomeFeedUiState.Loading)
    val uiState: StateFlow<HomeFeedUiState> = _uiState

    suspend fun getHomeData(year: String) = launchIO({

    }) {
        try {
            when (val res = homeRepository.getHomeData(year)) {
                is NetworkResponse.Error -> {
                    _uiState.update { _ ->
                        HomeFeedUiState.Error(res.body.message)
                    }
                }
                is NetworkResponse.Success -> {
                    _uiState.update { _ ->
                        HomeFeedUiState.Success(res.successBody)
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update { _ ->
                HomeFeedUiState.Error(e.message)
            }
        }
    }
}

sealed interface HomeFeedUiState {
    object Loading: HomeFeedUiState

    data class Error(val message: String?): HomeFeedUiState

    data class Success(val feed: HomeDataResponse): HomeFeedUiState

}