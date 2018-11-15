package com.taskstracker.Model.DataBase

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.taskstracker.Model.DataModels.Task

@Database(entities = [Task::class], version = 1)
abstract class TasksDataBase : RoomDatabase() {

    abstract fun TasksDBDao(): TasksDBDao

}