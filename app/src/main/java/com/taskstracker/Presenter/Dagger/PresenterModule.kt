package com.taskstracker.Presenter.Dagger

import com.taskstracker.Model.DataBase.DataBaseCache
import com.taskstracker.Model.DataBase.TasksDataBase
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(private val tasksDB: DataBaseCache) {

    @Provides
    fun providesTasksDB() : DataBaseCache{
        return tasksDB
    }


}