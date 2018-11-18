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

    private data class ViewHolderItem constructor(
        val id: TextView,
        val name: TextView,
        val status: TextView,
        val changeStatusButton: Button
    )

    @Inject
    lateinit var presenter: TasksListPresenter

    @Inject
    lateinit var context: Context

    private lateinit var statusOpen: String
    private lateinit var statusTraveling: String
    private lateinit var statusWorking: String

    private var colorOpen = 0
    private var colorTraveling = 0
    private var colorWorking = 0

    private var layoutInflater: LayoutInflater? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        initResorurces()
        var viewHolder: ViewHolderItem
        var resultView: View
        if (convertView == null) {
            if (layoutInflater == null) layoutInflater = LayoutInflater.from(context)
            resultView = layoutInflater!!.inflate(R.layout.tasks_list_row, parent, false)
            viewHolder = ViewHolderItem(
                resultView.findViewById(R.id.idTextView),
                resultView.findViewById(R.id.nameTextView),
                resultView.findViewById(R.id.statusTextView),
                resultView.findViewById(R.id.statusChangeButton)
            )
            resultView.tag = viewHolder
        } else {
            resultView = convertView
            viewHolder = convertView.tag as ViewHolderItem
        }

        val task = presenter.getTask(position)
        viewHolder.id.text = task.id.toString()
        viewHolder.name.text = task.name

        if (task.isLocked) {
            viewHolder.changeStatusButton.visibility = View.GONE
        } else {
            viewHolder.changeStatusButton.visibility = View.VISIBLE
            viewHolder.changeStatusButton.setOnClickListener { presenter.updateTask(position) }
        }

        when (task.status) {
            Task.OPEN -> {
                resultView.setBackgroundColor(colorOpen)
                viewHolder.status.text = statusOpen
            }
            Task.TRAVELING -> {
                resultView.setBackgroundColor(colorTraveling)
                viewHolder.status.text = statusTraveling
            }
            Task.WORKING -> {
                resultView.setBackgroundColor(colorWorking)
                viewHolder.status.text = statusWorking
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

    private fun initResorurces() {
        if (!(::statusOpen.isInitialized)) {
            statusOpen = context.resources.getText(R.string.status).toString() + ": " +
                    context.resources.getText(R.string.status_open).toString()
            statusTraveling = context.resources.getText(R.string.status).toString() + ": " +
                    context.resources.getText(R.string.status_traveling).toString()
            statusWorking = context.resources.getText(R.string.status).toString() + ": " +
                    context.resources.getText(R.string.status_working).toString()

            colorOpen = ContextCompat.getColor(context, R.color.light_blue)
            colorTraveling = ContextCompat.getColor(context, R.color.light_green)
            colorWorking = ContextCompat.getColor(context, R.color.light_pink)
        }
    }

}