package com.mycodeflow.rickandmortycharsapp.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mycodeflow.rickandmortycharsapp.data.model.CharItem

@Database(entities = [CharItem::class], version = 2)
@TypeConverters(CharTypeConverter::class)
abstract class CharsDataBase: RoomDatabase() {

    abstract fun getCharsDao(): CharsDao

    companion object {
        fun getInstance(context: Context): CharsDataBase{
            return Room.databaseBuilder(
                    context,
                    CharsDataBase::class.java,
                    "chars_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}