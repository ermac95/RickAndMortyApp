package com.mycodeflow.rickandmortycharsapp.data.datasource


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mycodeflow.rickandmortycharsapp.data.model.Origin


internal class CharTypeConverter {

    @TypeConverter
    fun originToJson(origin: Origin): String {
        val originTypeToken = object : TypeToken<Origin>() {}.type
        return Gson().toJson(origin, originTypeToken)
    }

    @TypeConverter
    fun originFromJson(originString: String): Origin {
        val originTypeToken = object : TypeToken<Origin>(){}.type
        return Gson().fromJson(originString, originTypeToken)
    }
}

