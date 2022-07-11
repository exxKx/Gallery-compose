package com.mobile.gallery

import android.app.Application
import com.mobile.gallery.dagger.DaggerRepositoryComponent
import com.mobile.gallery.dagger.ImagesModule
import com.mobile.gallery.dagger.RepositoryComponent

class App : Application() {

    companion object {
        val repositoryComponent get() = instance.repositoryComponent

        private lateinit var instance: App
    }

    lateinit var repositoryComponent : RepositoryComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        repositoryComponent = DaggerRepositoryComponent
            .builder()
            .build()
    }
}