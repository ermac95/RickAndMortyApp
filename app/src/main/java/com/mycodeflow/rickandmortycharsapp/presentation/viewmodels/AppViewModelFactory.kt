package com.mycodeflow.rickandmortycharsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycodeflow.rickandmortycharsapp.domain.repository.CharListRepository
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class AppViewModelFactory @Inject constructor(
    private val charsListRepo: CharListRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass){
        CharViewModel::class.java -> CharViewModel(charsListRepo)
        else -> throw IllegalArgumentException("wrong dependencies")
    } as T
}