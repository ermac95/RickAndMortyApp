package com.mycodeflow.rickandmortycharsapp.di

import android.content.Context
import com.mycodeflow.rickandmortycharsapp.data.datasource.CharsDao
import com.mycodeflow.rickandmortycharsapp.data.datasource.CharsDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): CharsDataBase {
        return CharsDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCharDao(appDataBase: CharsDataBase): CharsDao{
        return appDataBase.getCharsDao()
    }
}