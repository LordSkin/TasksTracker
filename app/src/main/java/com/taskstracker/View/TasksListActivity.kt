package com.taskstracker.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.taskstracker.Presenter.TasksListPresenter
import com.taskstracker.R
import com.taskstracker.Root.App
import javax.inject.Inject

/**
 * Activity for tasksViewList, also view object
 */
class TasksListActivity : AppCompatActivity(), TasksListView {

    @Inject
    lateinit var tasksListPresenter: TasksListPresenter

    private lateinit var loadingIndicator: ProgressBar
    private lateinit var listView: ListView
    private lateinit var listAdapter: TasksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)

        App.appComponent.appComponent.inject(this)

        loadingIndicator = findViewById(R.id.progressBar)
        listView = findViewById(R.id.tasksListView)
        listAdapter = TasksListAdapter()
        App.appComponent.appComponent.inject(listAdapter)

        tasksListPresenter.setTasksListView(this)
    }

    override fun loadListView() {
        runOnUiThread {
            listView.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            loadingIndicator.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }
    }

    override fun showLoading() {
        runOnUiThread {
            listView.visibility = View.GONE
            loadingIndicator.visibility = View.VISIBLE
        }

    }

    override fun hideLoading() {
        runOnUiThread {
            listView.visibility = View.VISIBLE
            loadingIndicator.visibility = View.GONE
        }
    }

    override fun showError(resId: Int) {
        runOnUiThread {
            Toast.makeText(this, resources.getString(resId), Toast.LENGTH_SHORT).show()
        }
    }

    override fun updateView() {
        runOnUiThread {
            listAdapter.notifyDataSetChanged()
        }

    }
}
