package com.taskstracker.Root.Dagger

import android.content.Context
import com.taskstracker.Presenter.AppPresenter
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val appPresenter: AppPresenter, private val context: Context) {

    @Provides
    fun provideContext() : Context{
        return context
    }

}