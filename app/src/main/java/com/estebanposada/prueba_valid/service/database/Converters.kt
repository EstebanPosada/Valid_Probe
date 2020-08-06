package com.estebanposada.prueba_valid.service.database

import androidx.room.TypeConverter
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.model.Image
import com.estebanposada.prueba_valid.service.model.Streamable
import com.estebanposada.prueba_valid.service.model.attr
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun streamToString(value: Streamable): String = gson.toJson(value)

    @TypeConverter
    fun stringToStream(data: String): Streamable = gson.fromJson(data, Streamable::class.java)

    @TypeConverter
    fun listImageToString(list: List<Image>): String = gson.toJson(list)

    @TypeConverter
    fun artistToString(value: Artist): String = gson.toJson(value)

    @TypeConverter
    fun stringToArtist(data: String): Artist = gson.fromJson(data, Artist::class.java)


    @TypeConverter
    fun stringToImageList(data: String?): List<Image> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Image>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun attrToString(value: attr): String = gson.toJson(value)

    @TypeConverter
    fun stringToAttr(data: String): attr = gson.fromJson(data, attr::class.java)

}