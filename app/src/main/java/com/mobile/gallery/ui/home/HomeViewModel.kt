package com.mobile.gallery.ui.home

import com.mobile.gallery.App
import com.mobile.gallery.data.repository.ImagesRepository
import com.mobile.gallery.domain.usecases.HomeUseCases
import com.mobile.gallery.ui.base.BaseViewModel
import com.mobile.gallery.ui.home.HomeEffect.LoadImages
import com.mobile.gallery.ui.home.HomeMutation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : BaseViewModel<HomeState, HomeMutation, HomeAction, HomeEffect>(HomeState()) {

    @Inject
    lateinit var repository: ImagesRepository

    val homeUseCases : HomeUseCases

    init {
        App.repositoryComponent.inject(this)

        homeUseCases = HomeUseCases(repository)
        mutation(Init)
    }

    override fun update(
        state: HomeState,
        mutation: HomeMutation
    ): Pair<HomeState, List<HomeEffect>?> {
        return when (mutation) {
            Init -> HomeState().copy(progress = true) to listOf(LoadImages(0))
            is ImagesReceived -> state.copy(
                progress = false,
                images = state.images?.plus(mutation.images) ?: mutation.images) to null
            LoadMore -> {
                when {
                    state.endReached -> state.copy(progress = false) to null // todo
                    !state.progress && !state.images.isNullOrEmpty() -> state.copy(progress = true) to listOf(LoadImages(state.images.size))
                    else -> state to null
                }
            }
            is SaveScrollPos -> state.copy(resumed = false, lastScrollPos = mutation.pos) to null
            ClearScrollPos -> state.copy(lastScrollPos = null) to null
            Resume -> state.copy(resumed = true) to null
        }
    }

    override fun effect(effect: HomeEffect) {
        when (effect) {
            is LoadImages ->
                launch(Dispatchers.IO) {
                    val result = homeUseCases.loadMore(effect.fromIndex)
                    mutation(ImagesReceived(result))
                }
        }
    }


}