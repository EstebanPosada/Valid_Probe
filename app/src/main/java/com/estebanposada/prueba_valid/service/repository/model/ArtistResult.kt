package com.estebanposada.prueba_valid.service.repository.model

import java.lang.Exception

sealed class ArtistResult {
    data class Success(val data: List<Artist>):ArtistResult()
//    data class Success(val data: ArtistObject):ArtistResult()
    data class Error(val error: Exception):ArtistResult()
}