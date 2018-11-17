package com.taskstracker.View

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.taskstracker.Model.DataModels.Task
import com.taskstracker.Presenter.TasksListPresenter
import com.taskstracker.R
import javax.inject.Inject

/**
 * ListAdapter for tasks list
 */
class TasksListAdapter : BaseAdapter() {

    @Inject
    lateinit var presenter: TasksListPresenter

    @Inject
    lateinit var context: Context

    private var layoutInflater: LayoutInflater? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(context)

        val task = presenter.getTask(position)
        val resultView = layoutInflater!!.inflate(R.layout.tasks_list_row, null)
        resultView.findViewById<TextView>(R.id.idTextView).text = task.id.toString()
        resultView.findViewById<TextView>(R.id.nameTextView).text = task.name

        val button = resultView.findViewById<Button>(R.id.statusChangeButton)
        if (task.isLocked) {
            button.visibility = View.GONE
        } else {
            button.visibility = View.VISIBLE
            button.setOnClickListener {
                presenter.updateTask(position)
            }
        }

        val statusTextView = resultView.findViewById<TextView>(R.id.statusTextView)
        when (task.status) {
            Task.OPEN -> {
                resultView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue))
                statusTextView.text = context.resources.getText(R.string.status).toString() + ":" +
                        context.resources.getText(R.string.status_open).toString()
            }
            Task.TRAVELING -> {
                resultView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
                statusTextView.text = context.resources.getText(R.string.status).toString() + ": " +
                        context.resources.getText(R.string.status_traveling).toString()
            }
            Task.WORKING -> {
                resultView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_pink))
                statusTextView.text = context.resources.getText(R.string.status).toString() + ":" +
                        context.resources.getText(R.string.status_working).toString()
            }
        }




        return resultView
    }

    override fun getItem(position: Int): Any {
        return presenter.getTask(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return presenter.getTasksCount()
    }

}