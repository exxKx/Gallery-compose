package com.mobile.gallery.data.dto

import com.google.gson.annotations.SerializedName
import com.mobile.gallery.domain.model.Photo

data class PhotosDto(
    @SerializedName("photos") val photoInfo: PhotoInfoDto?
    )

data class PhotoInfoDto(
    @SerializedName("photo") val elements: List<PhotoDto>?
)

data class PhotoDto(
    @SerializedName("url_m") val url: String?,
    @SerializedName("title") val title: String?
) {
    fun toModel() = Photo(
        url = url ?: "",
        title = title ?: "",
    )
}