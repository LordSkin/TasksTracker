package com.taskstracker.View

import android.content.Context
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

class TasksListAdapter : BaseAdapter() {

    @Inject
    lateinit var presenter : TasksListPresenter

    @Inject
    lateinit var context : Context

    private var layoutInflater : LayoutInflater? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(layoutInflater==null) layoutInflater = LayoutInflater.from(context)

        val task = presenter.getTask(position)
        val resultView = layoutInflater!!.inflate(R.layout.tasks_list_row, null)
        resultView.findViewById<TextView>(R.id.idTextView).text = task.id.toString()
        resultView.findViewById<TextView>(R.id.nameTextView).text = task.name

        val button = resultView.findViewById<Button>(R.id.statusChangeButton)

        when(task.status){
            Task.OPEN -> button.text = "OPEN"
            Task.TRAVELING -> button.text = "TRAVELING"
            Task.WORKING -> button.text = "WORKING"
        }

        button.setOnClickListener {
            presenter.updateTask(position)
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