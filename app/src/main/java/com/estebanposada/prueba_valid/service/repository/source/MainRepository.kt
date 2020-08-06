package com.estebanposada.prueba_valid.service.repository.source

import androidx.paging.PagingData
import com.estebanposada.prueba_valid.service.model.Artist
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getData(query: String): Flow<PagingData<Artist>>
    suspend fun getArtistDetail(id: Long): Artist
}