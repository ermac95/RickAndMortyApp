package com.mycodeflow.rickandmortycharsapp.di

import android.content.Context
import com.mycodeflow.rickandmortycharsapp.presentation.ui.CharsInfoFragment
import com.mycodeflow.rickandmortycharsapp.presentation.ui.CharsListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataBaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(frag: CharsListFragment)

    fun inject(frag: CharsInfoFragment)
}