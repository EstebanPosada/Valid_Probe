package com.estebanposada.prueba_valid

import com.estebanposada.prueba_valid.di.AppComponent
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [])
interface TestComponent : AppComponent {
    val localDataSource: LocalDataSource

    @Component.Factory
    interface FactoryTest {
        fun create(): TestComponent
    }

}

@Module
class TestModules {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = ""

    @Provides
    @Singleton
    fun localDataSourceProvider(): LocalDataSource = FakeLocalDataSource()
}

class FakeLocalDataSource : LocalDataSource {

    var artists: List<Artist> = emptyList()

    override suspend fun isEmpty(): Boolean = artists.isEmpty()

    override suspend fun saveAll(artists: List<Artist>) {
        this.artists = artists
    }

    override suspend fun findById(id: Long): Artist = artists.first { it.id == id }

    override suspend fun getAll(): List<Artist> = artists
}

val defaultFakeArtists = listOf(
    mockArtist.copy(id = 1),
    mockArtist.copy(id = 2),
    mockArtist.copy(id = 3),
    mockArtist.copy(id = 4)
)