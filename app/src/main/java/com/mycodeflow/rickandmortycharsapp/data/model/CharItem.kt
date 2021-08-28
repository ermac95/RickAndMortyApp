package com.mycodeflow.rickandmortycharsapp.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharItem(
    @PrimaryKey
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
)