package com.taskstracker.Presenter

import com.taskstracker.Model.DataBase.DataBaseCache
import com.taskstracker.Model.DataModels.Task
import com.taskstracker.View.TasksListView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AppPresenter : TasksListPresenter {


    @Inject
    lateinit var dBConnector: DataBaseCache

    private var tasksListView: TasksListView? = null

    private val updateTaskObservable = PublishSubject.create<Int>()

    init {
        updateTaskObservable.observeOn(Schedulers.newThread())
            .subscribe(object : Observer<Int> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Int) {
                    tasksListView!!.showLoading()
                    val task = dBConnector.getAll()[t]
                    dBConnector.updateStatus(task.id, task.nextStatus())
                    tasksListView!!.updateView()
                    tasksListView!!.hideLoading()
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    fun loadingComplete() {
        if (tasksListView != null) {
            tasksListView!!.updateView()
            tasksListView!!.hideLoading()
        }
    }

    override fun getTasksCount(): Int {
        return dBConnector.countTasks()
    }

    override fun getTask(id: Int): Task {
        return dBConnector.getAll()[id]
    }

    override fun getTasks(): List<Task> {
        return dBConnector.getAll()
    }

    override fun updateTask(id: Int) {
        updateTaskObservable.onNext(id)
    }

    override fun setTasksListView(view: TasksListView) {
        this.tasksListView = view
        if (dBConnector != null) {
            tasksListView!!.updateView()
            tasksListView!!.hideLoading()
        }
    }
}