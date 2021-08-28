package com.mycodeflow.rickandmortycharsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycodeflow.rickandmortycharsapp.data.model.CharItem
import com.mycodeflow.rickandmortycharsapp.data.model.CharResponse
import com.mycodeflow.rickandmortycharsapp.domain.repository.CharListRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Collections.emptyList
import javax.inject.Inject

class CharViewModel @Inject constructor(
    val charListRepository: CharListRepository
): ViewModel() {

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
        Log.e(this::class.java.simpleName, "CoroutineExceptionHandler:$throwable")
    }

    private val _charsList = MutableStateFlow(emptyList<CharItem>())
    val charsList: StateFlow<List<CharItem>> = _charsList

    private val _currentChar = MutableStateFlow<CharItem?>(null)
    val currentChar: StateFlow<CharItem?> = _currentChar

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        updateCharsList()
    }

    private fun updateCharsList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            _loading.value = true
            val charCacheData = charListRepository.getCharsFromDb()
            if (charCacheData.isNotEmpty()){
                _charsList.value = charCacheData
                _loading.value = false
            } else {
                val response = charListRepository.getCharsFromWeb()
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        val charsData = response.body()
                        if (charsData!= null){
                            val charsList = convertWebDataToModel(charsData.results)
                            charListRepository.updateDataInLocalDb(charsList)
                            _charsList.value = charsList
                            _loading.value = false
                        } else {
                            onError("No chars found")
                        }
                    } else {
                        onError("Couldn't fetch data from server")
                    }
                }
            }
        }
    }

    fun updateCurrentChar(charId: Int) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            _loading.value = true
            val cachedChar = charListRepository.findCharById(charId)
            if (cachedChar != null){
                _currentChar.value = cachedChar
                _loading.value = false
            } else {
                val response = charListRepository.getCharById(charId)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        val charData = response.body()
                        if (charData!= null){
                            val charModel = charData.toModel()
                            _currentChar.value = charModel
                            _loading.value = false
                        } else {
                            onError("No char with such id found")
                        }
                    } else {
                        onError("Couldn't fetch data from server")
                    }
                }
            }
        }
    }

    private fun convertWebDataToModel(charsData: List<CharResponse>): List<CharItem> {
        return charsData.map { charResponse->
            (CharItem(
                    created = charResponse.created,
                    episode = charResponse.episode,
                    gender = charResponse.gender,
                    id = charResponse.id,
                    image = charResponse.image,
                    name = charResponse.name,
                    origin = charResponse.origin,
                    species = charResponse.species,
                    status = charResponse.status,
                    type = charResponse.type
            ))
        }
    }

    private fun CharResponse.toModel(): CharItem {
        return CharItem(
                created = this.created,
                episode = this.episode,
                gender = this.gender,
                id = this.id,
                image = this.image,
                name = this.name,
                origin = this.origin,
                species = this.species,
                status = this.status,
                type = this.type
        )
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.value = false
    }
}

