package com.estebanposada.prueba_valid.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Artist>>? = null

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

}

