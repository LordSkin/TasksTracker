package com.taskstracker.Model.DataModels;


import android.arch.persistence.room.*;

@Entity
public class Task {

    public static int OPEN = 0;
    public static int TRAVELING = 1;
    public static int WORKING = 2;

    public Task() {
    }

    public Task(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
