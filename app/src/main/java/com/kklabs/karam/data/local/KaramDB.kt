package com.kklabs.karam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kklabs.karam.data.local.dao.TasklogsDao
import com.kklabs.karam.data.local.entity.TasklogDbEntity
import com.kklabs.karam.data.util.adapter.CustomDateAdapter


@Database(
    entities = [TasklogDbEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    CustomDateAdapter::class
)
abstract class KaramDB : RoomDatabase() {

    abstract fun tasklogsDao(): TasklogsDao

}