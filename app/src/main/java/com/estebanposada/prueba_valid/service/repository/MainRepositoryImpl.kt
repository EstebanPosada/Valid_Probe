package com.estebanposada.prueba_valid.service.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.Artist
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val service: Api,
    private val apiKey: String
) : MainRepository {

    override fun getData(query: String): Flow<PagingData<Artist>> {
        Log.wtf("Fetching data", "Query: $query")
//        return Pager(
//            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//            pagingSourceFactory = { PagingSource(service, apiKey, query) }
//        ).flow
        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        //val pagingSourceFactory = { database.artistDao().(dbQuery) }

        return Pager(
            config = PagingConfig(pageSize = 7, enablePlaceholders = false),
            remoteMediator = ArtistsRemoteMediator(
                apiKey,
                query,
                service
            ),
            pagingSourceFactory = { PagingSource(service, apiKey, query) }
        ).flow
    }
}