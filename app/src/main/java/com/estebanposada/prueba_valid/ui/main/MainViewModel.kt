package com.estebanposada.prueba_valid.ui.main

import androidx.lifecycle.*
import com.estebanposada.prueba_valid.service.repository.MainRepository
import com.estebanposada.prueba_valid.service.repository.model.ArtistResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    val result: LiveData<ArtistResult> = queryLiveData.switchMap { query ->
        liveData {
            val repos = repository.getData(query).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }

}

