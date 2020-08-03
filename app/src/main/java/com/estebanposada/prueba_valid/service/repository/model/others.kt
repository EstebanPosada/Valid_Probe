package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class Top(
    @SerializedName("topartists") val topartists: Topartists
)


data class Topartists(

    @SerializedName("artist") val artist: List<Artist>,
    @SerializedName("@attr") val attr: attr
)

//data class Artist(
//    @SerializedName("name") val name: String,
//    @SerializedName("listeners") val listeners: Int,
//    @SerializedName("mbid") val mbid: String,
//    @SerializedName("url") val url: String,
//    @SerializedName("streamable") val streamable: Int,
//    @SerializedName("image") val image: List<Image>
//)
//
//data class Image(
//
//    val text: String,
//    val size: String
//)

//data class attr(
//    @SerializedName("country") val country: String,
//    @SerializedName("page") val page: Int,
//    @SerializedName("perPage") val perPage: Int,
//    @SerializedName("totalPages") val totalPages: Int,
//    @SerializedName("total") val total: Int
//)