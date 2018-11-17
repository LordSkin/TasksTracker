package com.taskstracker.InterfaceTests;

import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.taskstracker.Model.DataBase.DataBaseCache;
import com.taskstracker.Model.DataModels.Task;
import com.taskstracker.Model.TasksLockingManager;
import com.taskstracker.Presenter.AppPresenter;
import com.taskstracker.R;
import com.taskstracker.View.TasksListActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TasksListScreenTests {

    @Rule
    ActivityTestRule<TasksListActivity> activity = new ActivityTestRule<>(TasksListActivity.class);

    private List<Task> tasksList;
    private DataBaseCache mockedDbConnector;
    private TasksLockingManager mockedTasksLockingManager;
    private AppPresenter presenter;

    @Before
    public void init() {
        mockedDbConnector = mock(DataBaseCache.class);
        mockedTasksLockingManager = mock(TasksLockingManager.class);

        tasksList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tasksList.add(new Task(i, "name" + i, i % 3));
        }

        when(mockedDbConnector.getAll()).thenReturn(tasksList);
        when(mockedDbConnector.countTasks()).thenReturn(tasksList.size());

        presenter = ((AppPresenter) activity.getActivity().tasksListPresenter);

        presenter.dBConnector = mockedDbConnector;
        presenter.tasksLockingManager = mockedTasksLockingManager;
        presenter.setTasksListView(activity.getActivity());
        activity.getActivity().runOnUiThread(() -> activity.getActivity().updateView());
    }

    @Test
    public void testViewLoadedProperly() {
        onView(withId(R.id.tasksListView)).check(matches(isDisplayed()));
        onView(withId(R.id.tasksListView)).check(matches(hasChildCount(tasksList.size())));
    }

    @Test
    public void testDisappearingButtons() {
        onView(withId(R.id.statusChangeButton)).perform(click());
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.tasksListView)).atPosition(0).onChildView(withId(R.id.statusChangeButton)).check(matches(isDisplayed()));
        for (int i = 1; i < tasksList.size(); i++) {
            onData(anything()).inAdapterView(withId(R.id.tasksListView)).atPosition(i).onChildView(withId(R.id.statusChangeButton)).check(matches(not(isDisplayed())));
        }
    }

    @Test
    public void testStatusChanging() {
        onData(anything()).inAdapterView(withId(R.id.tasksListView)).atPosition(0).onChildView(withId(R.id.statusTextView)).check(matches(withText("status: OPEN")));

        onView(withId(R.id.statusChangeButton)).perform(click());
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.tasksListView)).atPosition(0).onChildView(withId(R.id.statusTextView)).check(matches(withText("status: TRAVELING")));

        onView(withId(R.id.statusChangeButton)).perform(click());
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.tasksListView)).atPosition(0).onChildView(withId(R.id.statusTextView)).check(matches(withText("status: WORKING")));

        onView(withId(R.id.statusChangeButton)).perform(click());
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.tasksListView)).atPosition(0).onChildView(withId(R.id.statusTextView)).check(matches(withText("status: OPEN")));
    }


}
