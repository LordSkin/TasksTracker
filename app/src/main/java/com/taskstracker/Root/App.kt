package com.taskstracker.Root

import android.app.Application
import com.taskstracker.Presenter.AppPresenter
import com.taskstracker.Presenter.Dagger.DaggerPresenterComponent
import com.taskstracker.Presenter.Dagger.PresenterComponent
import com.taskstracker.Presenter.Dagger.PresenterModule
import com.taskstracker.Root.Dagger.AppComponent
import com.taskstracker.Root.Dagger.AppModule
import com.taskstracker.Root.Dagger.DaggerAppComponent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class App : Application() {

    companion object appComponent{
        lateinit var appComponent : AppComponent
    }

    private val tasksCount = 20
    private val DBname = "tasksDB"
    private val appPresenter = AppPresenter()

    override fun onCreate() {
        super.onCreate()

        appComponent.appComponent = DaggerAppComponent.builder().appModule(AppModule(appPresenter, applicationContext)).build()

        val observable = object : Observable<PresenterComponent>() {
            override fun subscribeActual(observer: Observer<in PresenterComponent>) {
                observer.onNext(DaggerPresenterComponent.builder().presenterModule(PresenterModule(applicationContext, DBname, tasksCount)).build())
            }
        }

        observable.subscribeOn(Schedulers.newThread())
            .subscribe(object : Observer<PresenterComponent> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(presenterComponent: PresenterComponent) {
                    presenterComponent.inject(appPresenter)
                    appPresenter.loadingComplete()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })


    }


}