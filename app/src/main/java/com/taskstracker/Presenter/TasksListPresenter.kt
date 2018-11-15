package com.taskstracker.Presenter

import com.taskstracker.Model.DataModels.Task
import com.taskstracker.View.TasksListView

interface TasksListPresenter {
    fun getTasksCount(): Int
    fun getTask(id: Int): Task
    fun getTasks(): List<Task>
    fun updateTask(id : Int)
    fun setTasksListView(view: TasksListView)

}