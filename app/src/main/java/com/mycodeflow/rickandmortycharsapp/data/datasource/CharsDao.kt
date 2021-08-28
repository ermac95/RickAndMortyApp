package com.mycodeflow.rickandmortycharsapp.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mycodeflow.rickandmortycharsapp.data.model.CharItem

@Dao
interface CharsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChars(characters: List<CharItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharSelected(character: CharItem)

    @Query("SELECT * FROM CharItem")
    suspend fun getAllChars(): List<CharItem>

    @Query("SELECT * FROM CharItem WHERE id = :characterId")
    suspend fun getCharById(characterId: Int): CharItem?

}