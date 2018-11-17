package com.taskstracker;

import com.taskstracker.Model.DataModels.Task;
import com.taskstracker.Model.TasksLockingManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TasksLockingManagerTests {

    private TasksLockingManager testObject;
    private List<Task> tasksList;

    @Before
    public void init() {
        testObject = new TasksLockingManager();
        tasksList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tasksList.add(new Task(i, "task " + i, Task.OPEN));
        }
    }

    @Test
    public void testLockingPositive() {
        testObject.lockTasks(tasksList, 0, Task.WORKING);

        tasksList.stream().filter(task -> task.getId() != 0).forEach(task -> assertTrue(task.isLocked()));
    }

    @Test
    public void testLockingnegative() {
        testObject.lockTasks(tasksList, 0, Task.OPEN);

        tasksList.forEach(task -> assertTrue(!task.isLocked()));
    }

    @Test
    public void testLockingEmptyList() {
        List<Task> emptyList = new ArrayList<>();

        testObject.lockTasks(emptyList, 0, Task.OPEN);

        assertEquals(0, emptyList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullList() {
        testObject.lockTasks(null, 0, Task.OPEN);
    }

    @Test
    public void testPositiveInit() {
        tasksList.get(0).setStatus(Task.WORKING);

        testObject.initLocks(tasksList);

        tasksList.stream().filter(task -> task.getId() != 0).forEach(task -> assertTrue(task.isLocked()));
        assertFalse(tasksList.get(0).isLocked());
    }

    @Test
    public void testNegativeInit() {
        testObject.initLocks(tasksList);
        tasksList.forEach(task -> assertFalse(task.isLocked()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullInit() {
        testObject.initLocks(null);
    }
}
