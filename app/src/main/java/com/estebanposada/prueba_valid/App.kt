package com.estebanposada.prueba_valid

import android.app.Application
import com.estebanposada.prueba_valid.di.AppComponent
import com.estebanposada.prueba_valid.di.DaggerAppComponent

open class App : Application() {

    val appComponent: AppComponent by lazy { initializeComponent() }

    open fun initializeComponent(): AppComponent = DaggerAppComponent
        .factory()
        .create(applicationContext)

    override fun onCreate() {
        super.onCreate()
        //initDI()
    }
}