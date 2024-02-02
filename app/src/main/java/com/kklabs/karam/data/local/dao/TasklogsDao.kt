package com.kklabs.karam.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kklabs.karam.data.local.entity.TasklogDbEntity

@Dao
interface TasklogsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasklogs: List<TasklogDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(tasklog: TasklogDbEntity)

    @Query("SELECT * FROM tasklogdbentity WHERE taskId=:taskId ORDER BY id ")
    fun getTasklogs(taskId: Int): PagingSource<Int, TasklogDbEntity>

    @Query("DELETE FROM tasklogdbentity")
    suspend fun clearTasklogs()
}