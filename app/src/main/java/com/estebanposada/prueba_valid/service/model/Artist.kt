package com.estebanposada.prueba_valid.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Artists(
    @field:SerializedName("name") val name: String,
    //@PrimaryKey(autoGenerate = true)  @field:SerializedName("id") val id: Long,
    @field:SerializedName("mbid") val mbid: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("listeners") val listeners: String,
    @field:SerializedName("streamable") val streamable: String,
    @field:SerializedName("image") val image: List<Image>

)

@Entity
data class Artist(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mbid: String,
    val url: String,
    val listeners: String,
    val streamable: String,
    val image: List<Image>

)