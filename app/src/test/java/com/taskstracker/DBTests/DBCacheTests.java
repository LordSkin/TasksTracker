package com.taskstracker.DBTests;

import com.taskstracker.Model.DataBase.DataBaseCache;
import com.taskstracker.Model.DataBase.TasksDBDao;
import com.taskstracker.Model.DataModels.Task;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DBCacheTests {

    private DataBaseCache testObject;

    private List<Task> tasksList;

    @Before
    public void init() {
        TasksDBDao mockedDao = mock(TasksDBDao.class);

        tasksList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tasksList.add(new Task(i, "name" + i, i % 3));
        }

        when(mockedDao.getAll()).thenReturn(tasksList);
        when(mockedDao.getSize()).thenReturn(tasksList.size());
        testObject = new DataBaseCache(mockedDao);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> testedList = testObject.getAll();
        assertEquals(testedList, tasksList);
    }

    @Test
    public void testCount() {
        assertEquals(testObject.countTasks(), tasksList.size());
    }

    @Test
    public void testUpdateCorrect() {
        Task t = testObject.getAll().get(0);

        testObject.updateStatus(t.getId(), 1);

        assertEquals(1, testObject.getAll().get(0).getStatus());
    }

    @Test(expected = InvalidParameterException.class)
    public void testUpdateWrongIdTask() {
        testObject.updateStatus(-52, 1);
    }

    @Test
    public void correctAddtest() {
        int newId = 34;
        Task t = new Task(newId, "asdasd", Task.OPEN);
        testObject.addTask(t);

        assertEquals(testObject.getAll().stream().filter(task -> task.getId() == newId).findFirst().get(), t);
    }

    @Test
    public void wrongAddTest() {
        Task t = tasksList.get(0);
        testObject.addTask(t);

        assertEquals(testObject.getAll().stream().filter(task -> t.getId() == task.getId()).count(), 1);
    }

}
