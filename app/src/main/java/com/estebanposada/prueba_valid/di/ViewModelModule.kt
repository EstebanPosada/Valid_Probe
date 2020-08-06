package com.estebanposada.prueba_valid.di

import com.estebanposada.prueba_valid.service.repository.source.MainRepository
import com.estebanposada.prueba_valid.ui.artist.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun mainViewModel(repository: MainRepository) =
        MainViewModel(repository)
}