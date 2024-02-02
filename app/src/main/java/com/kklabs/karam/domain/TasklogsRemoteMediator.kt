package com.kklabs.karam.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kklabs.karam.domain.model.TasklogsComponentViewData

@OptIn(ExperimentalPagingApi::class)
class TasklogsRemoteMediator (

) : RemoteMediator<Int, TasklogsComponentViewData>() {

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TasklogsComponentViewData>
    ): MediatorResult {
        TODO("lolol")
    }
}