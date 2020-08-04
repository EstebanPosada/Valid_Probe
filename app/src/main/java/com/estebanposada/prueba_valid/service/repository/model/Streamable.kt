package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class Streamable(
    @SerializedName("#text") val text: Int,
    @SerializedName("fulltrack") val fulltrack: Int
)