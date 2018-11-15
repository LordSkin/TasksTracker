package com.taskstracker.Root

import android.app.Application
import android.arch.persistence.room.Room
import com.taskstracker.Model.DataBase.TasksDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}