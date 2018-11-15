package com.taskstracker.Model.DataBase

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.taskstracker.Model.DataModels.Task


/**
 * Data acces obiect for data base
 */
@Dao
interface TasksDBDao {
    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>

    @Query("UPDATE Task SET status = :newStatus WHERE id = :taskId")
    fun updateTask(taskId: Int, newStatus: Int)

    @Insert
    fun addTask(task: Task)

    @Query("SELECT COUNT(id) FROM Task")
    fun getSize(): Int
}