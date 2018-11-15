package com.taskstracker.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.taskstracker.Presenter.TasksListPresenter
import com.taskstracker.R
import com.taskstracker.Root.App
import javax.inject.Inject

class TasksListActivity : AppCompatActivity(), TasksListView {

    @Inject
    lateinit var tasksListPresenter : TasksListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)

        App.appComponent.appComponent.inject(this)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(resId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
