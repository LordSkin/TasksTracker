package com.taskstracker.Model.DataBase

import com.taskstracker.Model.DataModels.Task
import java.security.InvalidParameterException

/**
 * caches tasks from data base
 */
class DataBaseCache(private val tasksDBDao: TasksDBDao) {

    private var listOfTasks: List<Task> = tasksDBDao.getAll()

    fun getAll(): List<Task> {
        return listOfTasks
    }

    fun updateStatus(id: Int, newStatus: Int) {
        if (listOfTasks.stream().filter { t -> t.id == id }.count()==0L){
            throw InvalidParameterException("wrong id")
        }
        listOfTasks.stream().filter { t -> t.id == id }.forEach { t -> t.status = newStatus }
        tasksDBDao.updateTask(id, newStatus)
    }

    fun countTasks(): Int {
        return listOfTasks.size
    }

}