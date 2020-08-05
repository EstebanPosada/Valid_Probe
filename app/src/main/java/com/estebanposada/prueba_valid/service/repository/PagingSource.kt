package com.estebanposada.prueba_valid.service.repository

import androidx.paging.PagingSource
import com.estebanposada.prueba_valid.PAGE_INDEX
import com.estebanposada.prueba_valid.service.model.Artist
import retrofit2.HttpException
import java.io.IOException

class PagingSource(
    private val service: Api,
    private val apiKey: String,
    private val query: String
) : PagingSource<Int, Artist>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val position = params.key ?: PAGE_INDEX
        return try {
            val response = service.fetchTopArtists(apiKey, position, params.loadSize)
            val artists = response.topArtists.artist
            LoadResult.Page(
                data = artists,
                prevKey = if (position == PAGE_INDEX) null else position - 1,
                nextKey = if (artists.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}