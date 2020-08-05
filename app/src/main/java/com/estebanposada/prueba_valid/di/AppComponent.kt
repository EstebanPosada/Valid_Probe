package com.estebanposada.prueba_valid.di

import android.content.Context
import com.estebanposada.prueba_valid.ui.artist.ArtistsFragment
import com.estebanposada.prueba_valid.ui.detail.ArtistDetailFragment
import com.estebanposada.prueba_valid.ui.main.MainActivity
import com.estebanposada.prueba_valid.ui.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class])
interface AppComponent {

    val mainViewModel: MainViewModel

    fun inject(activity: MainActivity)
    fun inject(fragment: ArtistsFragment)
    fun inject(fragment: ArtistDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}