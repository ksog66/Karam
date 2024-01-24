package com.kklabs.karam.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiV1 {

    @GET("home")
    suspend fun getHomeData(): NetworkResponse<Nothing>

    @POST("users")
    suspend fun createUser(): NetworkResponse<Nothing>

    @POST("task")
    suspend fun createTask(): NetworkResponse<Nothing>

    @GET("task/{id}")
    suspend fun getTask(@Path(Keys.ID) id: Long): NetworkResponse<Nothing>

    @GET("task")
    suspend fun getTasks(): NetworkResponse<Nothing>

    @POST("tasklog")
    suspend fun createTasklog(): NetworkResponse<Nothing>

    @GET("tasklog")
    suspend fun getTasklogs(
        @Query(Keys.TASK_ID) taskId: Long,
        @Query(Keys.PAGE_KEY) pageKey: Int? = null
    ): NetworkResponse<Nothing>
}