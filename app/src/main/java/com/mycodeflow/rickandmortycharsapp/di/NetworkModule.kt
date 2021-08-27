package com.mycodeflow.rickandmortycharsapp.di

import com.mycodeflow.rickandmortycharsapp.data.api.RickAndMortyApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpBuilder = OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): RickAndMortyApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
            .create(RickAndMortyApi::class.java)
    }
}