package com.mobile.gallery.data

import com.google.gson.Gson
import com.mobile.gallery.data.dto.PhotosDto
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkManager : INetworkManager {

    private val networkManager : INetworkManager

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("format", "json")
                    .addQueryParameter("nojsoncallback", "1")
                    .addQueryParameter("api_key", "793fe0f08b0fcdfd5bca965e6ea7354c")
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        networkManager = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl("https://www.flickr.com/services/rest/")
            .build()
            .create(INetworkManager::class.java)


    }

    override suspend fun requestPhotos(extras: String, pageSize: Int, page: Int): Response<PhotosDto> {
        return networkManager.requestPhotos(extras, pageSize, page)
    }

}