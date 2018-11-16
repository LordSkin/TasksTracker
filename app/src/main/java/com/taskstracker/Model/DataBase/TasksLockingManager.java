package com.taskstracker.Model.DataBase;

import com.taskstracker.Model.DataModels.Task;

import java.util.List;

public class TasksLockingManager {
    public static boolean  lockTasks(List<Task> tasksList, Task updatedTask, int newStatus){
        return false;
    }
}
