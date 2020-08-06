package com.estebanposada.prueba_valid.ui.artist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.estebanposada.prueba_valid.data.source.FakeRepository
import com.estebanposada.prueba_valid.service.repository.source.MainRepository
import com.estebanposada.prueba_valid.ui.track.TrackViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mainRepository: FakeRepository

    @Before
    fun setupViewModel(){
        mainRepository = FakeRepository()


        mainViewModel = MainViewModel((mainRepository))

    }

    @Test
    fun `when Item clicked `() = runBlocking {

    }
}