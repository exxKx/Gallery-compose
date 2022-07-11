package com.mobile.gallery.dagger

import com.mobile.gallery.data.INetworkManager
import com.mobile.gallery.data.NetworkManager
import com.mobile.gallery.data.repository.IImagesRepository
import com.mobile.gallery.data.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImagesModule {

    @Provides
    @Singleton
    fun networkManager() : INetworkManager = NetworkManager

    @Provides
    @Singleton
    fun provideBroadcastRepository(networkManager: INetworkManager): IImagesRepository {
        return ImagesRepository(networkManager)
    }

}