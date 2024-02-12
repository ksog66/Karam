package com.kklabs.karam.data.remote

import com.kklabs.karam.data.remote.request.CreateTaskRequest
import com.kklabs.karam.data.remote.request.CreateTasklogRequest
import com.kklabs.karam.data.remote.request.CreateUserRequest
import com.kklabs.karam.data.remote.request.UpdateTaskRequest
import com.kklabs.karam.data.remote.response.DataResponse
import com.kklabs.karam.data.remote.response.HomeDataResponse
import com.kklabs.karam.data.remote.response.LogEntity
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

    @GET("/api/v1/home")
    suspend fun getHomeData(@Query(Keys.YEAR) year: Int): NetworkResponse<HomeDataResponse>

    @POST("/api/v1/user/")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): NetworkResponse<UserResponse>

    @POST("/api/v1/task")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): NetworkResponse<TaskResponse>

    @GET("/api/v1/task/{id}")
    suspend fun getTask(@Path(Keys.ID) id: Int): NetworkResponse<TaskResponse>

    @POST("/api/v1/task/{id}")
    suspend fun updateTask(
        @Body request: UpdateTaskRequest
    ): NetworkResponse<TaskResponse>

    @GET("/api/v1/task")
    suspend fun getTasks(): NetworkResponse<Nothing>

    @POST("/api/v1/tasklog")
    suspend fun createTasklog(
        @Body request: CreateTasklogRequest
    ): NetworkResponse<TasklogResponse>

    @GET("/api/v1/tasklog")
    suspend fun getTasklogs(
        @Query(Keys.TASK_ID) taskId: Int,
        @Query(Keys.PAGE_KEY) pageKey: Int? = null
    ): NetworkResponse<DataResponse<List<ModuleData<LogEntity>>>>
}