package com.mobile.gallery.ui.home

import com.mobile.gallery.domain.model.Photo

data class HomeState(
    val progress: Boolean = true,
    val images: List<Photo>? = null,
    val endReached: Boolean = false,
    val lastScrollPos: Int? = 0,
    val resumed: Boolean = false
)

sealed class HomeMutation {
    object Init : HomeMutation()
    data class ImagesReceived(val images: List<Photo>) : HomeMutation()
    object LoadMore : HomeMutation()
    data class SaveScrollPos(val pos: Int): HomeMutation()
    object ClearScrollPos: HomeMutation()
    object Resume: HomeMutation()
}

sealed class HomeAction

sealed class HomeEffect {
    data class LoadImages(val fromIndex: Int) : HomeEffect()
}

