package com.estebanposada.prueba_valid.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.repository.source.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Artist>>? = null

    private val _detail = MutableLiveData<Artist>()
    val detail: LiveData<Artist>
        get() = _detail


    fun searchArtists(query: String): Flow<PagingData<Artist>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Artist>> = repository.getData(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getArtistDetail(id: Long) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) { repository.getArtistDetail(id) }
            _detail.value = response
        }
    }

}

