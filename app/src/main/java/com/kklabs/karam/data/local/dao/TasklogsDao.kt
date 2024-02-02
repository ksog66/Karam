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
    fun insertOne(tasklog: TasklogDbEntity)

    @Query("SELECT * FROM TasklogDbEntity ORDER BY dateCreated DESC")
    fun getTasklogs(): PagingSource<Int, TasklogDbEntity>
}