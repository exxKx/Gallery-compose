package com.mobile.gallery.data.repository

import com.mobile.gallery.domain.model.Photo

interface IImagesRepository {

    suspend fun loadImages(extras: String, pageSize: Int, page: Int) : List<Photo>
}