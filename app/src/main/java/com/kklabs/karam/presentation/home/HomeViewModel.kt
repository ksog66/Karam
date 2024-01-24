package com.kklabs.karam.presentation.home

import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.repo.HomeRepository
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {


    suspend fun getHomeData() = launchIO({

    }) {

    }
}