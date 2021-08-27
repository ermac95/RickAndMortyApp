package com.mycodeflow.rickandmortycharsapp.data.api

import com.mycodeflow.rickandmortycharsapp.data.model.CharResponse
import com.mycodeflow.rickandmortycharsapp.data.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharsList(): Response<CharactersResponse>

    @GET("character/{char_id}")
    suspend fun getCharById(
        @Path("char_id") charId: Int
    ): Response<CharResponse>
}