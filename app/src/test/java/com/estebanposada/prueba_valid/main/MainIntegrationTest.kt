package com.estebanposada.prueba_valid.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.estebanposada.prueba_valid.CoroutineTestRule
import com.estebanposada.prueba_valid.FakeLocalDataSource
import com.estebanposada.prueba_valid.data.source.FakeRepository
import com.estebanposada.prueba_valid.defaultFakeArtists
import com.estebanposada.prueba_valid.getOrAwaitValue
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.ui.artist.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@ExperimentalCoroutinesApi
class MainIntegrationTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var vm: MainViewModel
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = FakeRepository()
        val artist = Artist("Name", 11, "mbid", "url", "1234", "0", listOf())
        repository.addArtist(artist)
        vm = MainViewModel(repository)
        localDataSource = FakeLocalDataSource()
        localDataSource.artists = defaultFakeArtists
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `data loaded from local source`() = runBlockingTest {
        vm.getArtistDetail(id = 11)
        val value = vm.detail.getOrAwaitValue()

        assertThat(value, ArgumentMatchers.notNull())
    }
}