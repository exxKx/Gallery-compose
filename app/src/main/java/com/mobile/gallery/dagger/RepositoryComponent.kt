package com.mobile.gallery.dagger

import com.mobile.gallery.ui.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ ImagesModule::class ])
interface RepositoryComponent {

    fun inject(viewModel: HomeViewModel)
}