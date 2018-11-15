package com.taskstracker;

import com.taskstracker.Model.DataBase.TasksGenerator;
import com.taskstracker.Model.DataModels.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TasksGeneratorTests {

    @Before
    public void init(){

    }

    @Test
    public void testGeneratingCorrect(){
        int count = 20;

        List<Task> tasks =  TasksGenerator.generateTasks(count);

        assertEquals(tasks.size(), count);
        assertEquals(tasks.stream().filter(t -> t.getId()>=0 && t.getId()<20 && t.getName().startsWith("Task") && t.getStatus()==Task.OPEN).count(), count);
    }

    @Test
    public void testGeneratingInCorrect(){
        int count = -1;

        List<Task> tasks =  TasksGenerator.generateTasks(count);

        assertEquals(tasks.size(), 0);
        assertEquals(tasks.stream().filter(t -> t.getId()>=0 && t.getId()<20 && t.getName().startsWith("Task") && t.getStatus()==Task.OPEN).count(), 0);

    }

    @Test
    public void testGenerateZero(){
        int count =0;

        List<Task> tasks =  TasksGenerator.generateTasks(count);

        assertEquals(tasks.size(), count);
        assertEquals(tasks.stream().filter(t -> t.getId()>=0 && t.getId()<20 && t.getName().startsWith("Task") && t.getStatus()==Task.OPEN).count(), count);
    }

}
