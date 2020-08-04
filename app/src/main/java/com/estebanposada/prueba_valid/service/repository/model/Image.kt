package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text") val text: String,
    @SerializedName("size") val size: String
)