package com.mobile.gallery.data

import com.mobile.gallery.data.dto.PhotosDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkManager {

    @GET("?method=flickr.interestingness.getList")
    suspend fun requestPhotos(
        @Query("extras") extras: String,
        @Query("per_page") pageSize: Int,
        @Query("page") page: Int
    ): Response<PhotosDto>
}