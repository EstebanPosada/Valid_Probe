package com.estebanposada.prueba_valid.service.repository

import androidx.paging.PagingData
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.model.ArtistResult
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getData(query: String): Flow<PagingData<Artist>>
    suspend fun getArtistDetail(id: Long): Artist
}