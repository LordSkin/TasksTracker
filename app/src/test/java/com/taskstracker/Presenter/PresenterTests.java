package com.taskstracker.Presenter;

import com.taskstracker.Model.DataBase.DataBaseCache;
import com.taskstracker.Model.TasksLockingManager;
import com.taskstracker.Model.DataModels.Task;
import com.taskstracker.View.TasksListView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class PresenterTests {

    private AppPresenter testObject;
    private List<Task> tasksList;
    private DataBaseCache mockedDbConnector;
    private TasksLockingManager mockedTasksLockingManager;

    @Before
    public void init(){
        testObject = new AppPresenter();
        mockedDbConnector = mock(DataBaseCache.class);
        mockedTasksLockingManager = mock(TasksLockingManager.class);

        tasksList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tasksList.add(new Task(i, "name"+i, i%3));
        }

        when(mockedDbConnector.getAll()).thenReturn(tasksList);
        when(mockedDbConnector.countTasks()).thenReturn(tasksList.size());

        testObject.dBConnector = mockedDbConnector;
        testObject.tasksLockingManager = mockedTasksLockingManager;
        testObject.setTasksListView(mock(TasksListView.class));
    }

    @Test
    public void testGetingTasks(){
        assertEquals(testObject.getTasks(), tasksList);
        assertEquals(testObject.getTask(0), tasksList.get(0));
    }

    @Test
    public void testTasksCount(){
        assertEquals(testObject.getTasksCount(), tasksList.size());
    }

    @Test
    public void testUpdatingTasks(){
        doAnswer(invocation -> {
            Object arg0 = invocation.getArguments()[0];
            Object arg1 = invocation.getArguments()[1];

            assertEquals(1, arg0);
            assertEquals(2, arg1);
            return null;
        }).when(mockedDbConnector).updateStatus(anyInt(),anyInt());

        testObject.updateTask(1);
    }
}
