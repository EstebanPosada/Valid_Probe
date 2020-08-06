package com.estebanposada.prueba_valid.di

import android.content.Context
import com.estebanposada.prueba_valid.ui.track.detail.TrackDetailFragment
import com.estebanposada.prueba_valid.ui.track.TracksFragment
import com.estebanposada.prueba_valid.ui.artist.ArtistsFragment
import com.estebanposada.prueba_valid.ui.artist.detail.ArtistDetailFragment
import com.estebanposada.prueba_valid.ui.main.MainActivity
import com.estebanposada.prueba_valid.ui.artist.MainViewModel
import com.estebanposada.prueba_valid.ui.track.TrackViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {

    val mainViewModel: MainViewModel
    val trackViewModel: TrackViewModel

    fun inject(activity: MainActivity)
    fun inject(fragment: ArtistsFragment)
    fun inject(fragment: ArtistDetailFragment)
    fun inject(fragment: TracksFragment)
    fun inject(fragment: TrackDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}