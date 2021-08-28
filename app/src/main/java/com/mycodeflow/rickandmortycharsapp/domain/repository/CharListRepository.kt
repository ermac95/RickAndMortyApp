package com.mycodeflow.rickandmortycharsapp.domain.repository

import com.mycodeflow.rickandmortycharsapp.data.api.RickAndMortyApi
import com.mycodeflow.rickandmortycharsapp.data.datasource.CharsDao
import com.mycodeflow.rickandmortycharsapp.data.model.CharItem
import com.mycodeflow.rickandmortycharsapp.data.model.CharResponse
import com.mycodeflow.rickandmortycharsapp.data.model.CharactersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

interface CharListRepositorySource{

    suspend fun getCharsFromWeb(): Response<CharactersResponse>

    suspend fun getCharById(id: Int): Response<CharResponse>
/*

    suspend fun getCharsFromDb(): List<CharItem>

    suspend fun findCharById(id: Int)

    suspend fun updateDataInLocalDb(chars: List<CharItem>)

 */

}


class CharListRepository @Inject constructor(
    val netWorkApi: RickAndMortyApi
    //val localDataSource: CharsDao
): CharListRepositorySource {

    override suspend fun getCharsFromWeb(): Response<CharactersResponse>{
        return netWorkApi.getCharsList()
    }

    override suspend fun getCharById(id: Int): Response<CharResponse> {
        return netWorkApi.getCharById(id)
    }
    /*
    override suspend fun getCharsFromDb(): List<CharItem> = withContext(Dispatchers.IO) {
        localDataSource.getAllChars()
    }

    override suspend fun findCharById(id: Int) {

    }

    override suspend fun updateDataInLocalDb(chars: List<CharItem>) {
        localDataSource.insertAllChars(chars)
    }

     */
}