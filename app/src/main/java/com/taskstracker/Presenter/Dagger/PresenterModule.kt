package com.taskstracker.Presenter.Dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.taskstracker.Model.DataBase.DataBaseCache
import com.taskstracker.Model.DataBase.TasksDataBase
import com.taskstracker.Model.DataBase.TasksGenerator
import com.taskstracker.Model.DataBase.TasksLockingManager
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(context : Context, dbName : String, private val tasksCount : Int) {

    private var tasksDB : DataBaseCache = DataBaseCache(
        Room.databaseBuilder(context, TasksDataBase::class.java, dbName+1)
            .build()
            .TasksDBDao()
    )

    init {
        fillDB()
    }

    @Provides
    fun providesTasksDB() : DataBaseCache{
        return tasksDB
    }

    private fun fillDB() {
        if (tasksDB.countTasks() == 0) {
            val tasks = TasksGenerator.generateTasks(tasksCount)
            for (t in tasks) {
                tasksDB.addTask(t)
            }
        }
    }

    @Provides
    fun provideTasksLockingManager() : TasksLockingManager{
        return TasksLockingManager()
    }


}