package com.estebanposada.prueba_valid.data.source

import androidx.paging.PagingData
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.repository.source.MainRepository
import kotlinx.coroutines.flow.Flow

class FakeRepository : MainRepository {

    private val artistData: LinkedHashMap<Long, Artist> = LinkedHashMap()

    override fun getData(query: String): Flow<PagingData<Artist>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtistDetail(id: Long): Artist = artistData[id]!!

    fun addArtist(vararg artists: Artist) {
        for (artist in artists){
            artistData[artist.id] = artist
        }
    }
}