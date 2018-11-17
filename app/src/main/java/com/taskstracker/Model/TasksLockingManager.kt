package com.taskstracker.Model

import com.taskstracker.Model.DataModels.Task

open class TasksLockingManager {

    fun lockTasks(tasks: List<Task>, updatedPos: Int, newStatus: Int) {
        if (newStatus == Task.OPEN) {
            tasks.stream().forEach { t -> t.setIsLocked(false) }
        } else {
            tasks.stream().forEach { t -> t.setIsLocked(true) }
            tasks[updatedPos].setIsLocked(false)
        }
    }

    fun initLocks(tasks: List<Task>) {
        val runningTask = tasks.stream().filter { t -> t.status != Task.OPEN }.findFirst()
        if (runningTask.isPresent) {
            tasks.stream().forEach { t -> t.setIsLocked(true) }
            runningTask.get().setIsLocked(false)
        } else {
            tasks.stream().forEach { t -> t.setIsLocked(false) }
        }
    }
}