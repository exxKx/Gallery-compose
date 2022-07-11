package com.mobile.gallery.domain.usecases

import com.mobile.gallery.data.repository.ImagesRepository
import com.mobile.gallery.domain.model.Photo

class HomeUseCases(val repository: ImagesRepository) {

    companion object {
        const val PAGE_SIZE = 20
    }

    enum class Extras(val code: String) { MEDIUM_SIZE("url_m") }

    suspend fun loadMore(fromIndex: Int): List<Photo> {
        return repository.loadImages(Extras.MEDIUM_SIZE.code, PAGE_SIZE, fromIndex / PAGE_SIZE)
    }
}