package com.estebanposada.prueba_valid.ui.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.estebanposada.prueba_valid.service.model.Track
import com.estebanposada.prueba_valid.service.repository.source.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackViewModel @Inject constructor(
    private val repository: TrackRepository
) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Track>>? = null

    private val _detail = MutableLiveData<Track>()
    val detail: LiveData<Track>
        get() = _detail


    fun searchTracks(query: String): Flow<PagingData<Track>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Track>> = repository.getData(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getTrackDetail(id: Long) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) { repository.getTrackDetail(id) }
            _detail.value = response
        }
    }
}