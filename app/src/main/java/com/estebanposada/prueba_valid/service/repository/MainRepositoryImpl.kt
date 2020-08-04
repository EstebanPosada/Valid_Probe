package com.estebanposada.prueba_valid.service.repository

import android.util.Log
import com.estebanposada.prueba_valid.GITHUB_STARTING_PAGE_INDEX
import com.estebanposada.prueba_valid.service.repository.model.Artist
import com.estebanposada.prueba_valid.service.repository.model.ArtistResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class MainRepositoryImpl(
    /* private val remoteDataSource: DataSource,
     private val localDataSource: DataSource,*/
    private val service: Api
) : MainRepository {
    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Artist>()

    // keep channel of results. The channel allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = ConflatedBroadcastChannel<ArtistResult>()

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    override suspend fun getData(query: String): Flow<ArtistResult> {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults.asFlow()
    }

    override suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry(query: String) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        val apiQuery = "829751643419a7128b7ada50de590067"
        try {
            val response = service.fetchTopArtists(apiQuery, lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d("GithubRepository", "response $response")
            val repos = response.topArtists.artist
            inMemoryCache.addAll(repos)
            val reposByName = reposByName(query)
            searchResults.offer(ArtistResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(ArtistResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(ArtistResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<Artist> {
        // from the in memory cache select only the repos whose name or description matches
        // the query. Then order the results.
        return inMemoryCache.filter {
            it.name.contains(query, true)
        }.sortedWith(compareByDescending { it.name })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
}