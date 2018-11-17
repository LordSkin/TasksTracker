package com.taskstracker.Model.DataBase;

import com.taskstracker.Model.DataModels.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * generates x tasks with OPEN status
 */
public class TasksGenerator {

    public static List<Task> generateTasks(int count) {
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(new Task(i, "Task " + i, Task.OPEN));
        }
        return result;
    }
}
