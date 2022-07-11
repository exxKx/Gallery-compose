package com.mobile.gallery.data.repository

import com.mobile.gallery.data.INetworkManager
import com.mobile.gallery.data.dto.PhotoDto
import com.mobile.gallery.domain.model.Photo
import javax.inject.Inject

class ImagesRepository @Inject constructor(private val networkManager: INetworkManager) : IImagesRepository {

    override suspend fun loadImages(extras: String, pageSize: Int, page: Int) : List<Photo> {
        return networkManager.requestPhotos(extras, pageSize, page)
            .let { response -> response.body()?.photoInfo?.elements?.map(PhotoDto::toModel) ?: emptyList() }
    }

}