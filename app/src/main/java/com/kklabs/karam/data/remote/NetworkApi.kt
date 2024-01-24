package com.kklabs.karam.data.remote

import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.remote.response.DataResponse
import com.kklabs.karam.data.remote.response.HomeDataResponse
import com.kklabs.karam.data.remote.response.ModuleData
import com.kklabs.karam.data.remote.response.TaskResponse
import com.kklabs.karam.data.remote.response.TasklogResponse
import com.kklabs.karam.data.remote.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {

    @GET("home")
    suspend fun getHomeData(): NetworkResponse<HomeDataResponse>

    @POST("users")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): NetworkResponse<UserResponse>

    @POST("task")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): NetworkResponse<TaskResponse>

    @GET("task/{id}")
    suspend fun getTask(@Path(Keys.ID) id: Long): NetworkResponse<TaskResponse>

    @GET("task")
    suspend fun getTasks(): NetworkResponse<Nothing>

    @POST("tasklog")
    suspend fun createTasklog(
        @Body request: CreateTasklogRequest
    ): NetworkResponse<TasklogResponse>

    @GET("tasklog")
    suspend fun getTasklogs(
        @Query(Keys.TASK_ID) taskId: Long,
        @Query(Keys.PAGE_KEY) pageKey: Int? = null
    ): NetworkResponse<DataResponse<List<ModuleData<*>>>>
}