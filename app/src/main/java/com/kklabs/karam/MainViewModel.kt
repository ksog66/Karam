package com.kklabs.karam

import com.kklabs.karam.data.ds.ConfigPreferences
import com.kklabs.karam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val configPreferences: ConfigPreferences
) : BaseViewModel() {

    val existingUser = configPreferences.existingUser

}