package com.estebanposada.prueba_valid

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}