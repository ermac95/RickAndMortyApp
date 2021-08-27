package com.mycodeflow.rickandmortycharsapp.di

import com.mycodeflow.rickandmortycharsapp.domain.repository.CharListRepository
import com.mycodeflow.rickandmortycharsapp.presentation.viewmodels.AppViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        charListRepository: CharListRepository
    ): AppViewModelFactory{
        return AppViewModelFactory(charListRepository)
    }
}