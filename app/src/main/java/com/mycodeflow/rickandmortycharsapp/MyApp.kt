package com.mycodeflow.rickandmortycharsapp

import android.app.Application
import com.mycodeflow.rickandmortycharsapp.di.AppComponent
import com.mycodeflow.rickandmortycharsapp.di.DaggerAppComponent


class MyApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.factory()
            .create(applicationContext)
    }
}