package com.taskstracker.View

interface TasksListView {
    fun showLoading()
    fun hideLoading()
    fun showError(resId : Int)
    fun updateView()
    fun loadListView()

}