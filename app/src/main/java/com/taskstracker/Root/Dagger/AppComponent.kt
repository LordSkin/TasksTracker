package com.taskstracker.Root.Dagger

import com.taskstracker.Presenter.AppPresenter
import com.taskstracker.View.TasksListActivity
import com.taskstracker.View.TasksListAdapter
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent{
    fun inject(tasksActivity : TasksListActivity)
    fun inject(tasksListAdapter: TasksListAdapter)
}