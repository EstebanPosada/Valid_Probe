package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class Artist(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("mbid") val mbid: String,
    @field:SerializedName("url") val url: String
)