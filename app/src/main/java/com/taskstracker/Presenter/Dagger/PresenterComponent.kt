package com.taskstracker.Presenter.Dagger

import com.taskstracker.Presenter.AppPresenter
import dagger.Component

@Component(modules = [PresenterModule::class])
interface PresenterComponent {
    fun inject(appPresenter: AppPresenter)
}