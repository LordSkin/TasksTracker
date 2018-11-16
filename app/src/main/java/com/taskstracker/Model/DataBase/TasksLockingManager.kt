package com.taskstracker.Model.DataBase

import com.taskstracker.Model.DataModels.Task

class TasksLockingManager() {
    fun lockTasks(tasks: List<Task>, updatedPos: Int, newStatus: Int) {
        if(newStatus==Task.OPEN){
            tasks.stream().forEach { t ->  t.setIsLocked(false)}
        }
        else{
            tasks.stream().forEach { t ->  t.setIsLocked(true)}
            tasks[updatedPos].setIsLocked(false)
        }
    }
}