package com.kklabs.karam.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kklabs.karam.data.local.KaramDB
import com.kklabs.karam.data.local.entity.RemoteKeys
import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.data.mapper.toTasklogDbEntity
import com.kklabs.karam.data.remote.NetworkResponse
import com.kklabs.karam.data.remote.response.LogEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TasklogsRemoteMediator(
    private val taskId: Int,
    private val service: DataSource,
    private val db: KaramDB
) : RemoteMediator<Int, TasklogDbEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TasklogDbEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val apiResponse = service.getTasklogs(taskId, page)

            when (apiResponse) {
                is NetworkResponse.Error -> {
                    MediatorResult.Error(Exception(apiResponse.body.message))
                }
                is NetworkResponse.Success -> {
                    val tasklogs = mutableListOf<TasklogDbEntity>()
                    apiResponse.successBody.data.forEach { moduleData ->
                        when (moduleData.moduleId) {
                            "task_logs" -> {
                                val taskLog =
                                    (moduleData.data as LogEntity.TasklogEntity).toTasklogDbEntity()
                                tasklogs.add(taskLog)
                            }

                            "log_date" -> {
                                val logDate =
                                    (moduleData.data as LogEntity.LogDateEntity).toTasklogDbEntity(taskId)
                                tasklogs.add(logDate)
                            }
                        }
                    }

                    val endOfPaginationReached = tasklogs.isEmpty()
                    db.withTransaction {
                        // clear all tables in the database
                        if (loadType == LoadType.REFRESH) {
                            db.remoteKeysDao().clearRemoteKeys()
                            db.tasklogsDao().clearTasklogs()
                        }
                        val prevKey = if (page == 0) null else page
                        val nextKey = if (endOfPaginationReached) null else apiResponse.successBody.paginationKey
                        val keys = tasklogs.map {
                            RemoteKeys(taskId = taskId, prevKey = prevKey, nextKey = nextKey)
                        }
                        db.remoteKeysDao().insertAll(keys)
                        db.tasklogsDao().insertAll(tasklogs)
                    }
                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        } catch (exception: NullPointerException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TasklogDbEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tasklog ->
                // Get the remote keys of the last item retrieved
                db.remoteKeysDao().remoteKeysTaskId(taskId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TasklogDbEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tasklog ->
                // Get the remote keys of the first items retrieved
                db.remoteKeysDao().remoteKeysTaskId(taskId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TasklogDbEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { tasklogId ->
                db.remoteKeysDao().remoteKeysTaskId(taskId)
            }
        }
    }
}