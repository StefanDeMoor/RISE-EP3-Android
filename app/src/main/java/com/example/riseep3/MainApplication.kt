package com.example.riseep3

import android.app.Application
import com.example.riseep3.data.AppContainer

class MainApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        container = AppContainer(this)
    }

    companion object {
        lateinit var instance: MainApplication
            private set
    }
}