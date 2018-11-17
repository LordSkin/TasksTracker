package com.taskstracker.Presenter

import com.taskstracker.Model.DataBase.DataBaseCache
import com.taskstracker.Model.DataModels.Task
import com.taskstracker.Model.TasksLockingManager
import com.taskstracker.R
import com.taskstracker.View.TasksListView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AppPresenter : TasksListPresenter {


    @Inject
    lateinit var dBConnector: DataBaseCache

    @Inject
    lateinit var tasksLockingManager: TasksLockingManager

    private var tasksListView: TasksListView? = null

    private val updateTaskObservable = PublishSubject.create<Int>()

    private var presenterLoaded = false
    private var viewLoaded = false

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
                    tasksLockingManager.lockTasks(dBConnector.getAll(), t, task.nextStatus())
                    dBConnector.updateStatus(task.id, task.nextStatus())
                    tasksListView!!.updateView()
                    tasksListView!!.hideLoading()
                }

                override fun onError(e: Throwable) {
                    tasksListView!!.showError(R.string.unknown_error)
                }
            })
    }

    fun loadingComplete() {
        tasksLockingManager.initLocks(dBConnector.getAll())
        presenterLoaded = true
        if (viewLoaded) {
            tasksListView!!.loadListView()
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

    override fun updateTask(position: Int) {
        updateTaskObservable.onNext(position)
    }

    override fun setTasksListView(view: TasksListView) {
        viewLoaded = true
        this.tasksListView = view
        if (presenterLoaded) {
            tasksListView!!.loadListView()
        }


    }
}