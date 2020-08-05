package com.estebanposada.prueba_valid.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Artist(
    @field:SerializedName("name") val name: String,
    @PrimaryKey(autoGenerate = true)  @field:SerializedName("id") val id: Long,
    @field:SerializedName("mbid") val mbid: String,
    @field:SerializedName("url") val url: String
)