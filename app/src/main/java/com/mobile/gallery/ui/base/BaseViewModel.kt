package com.mobile.gallery.ui.base

import androidx.annotation.CallSuper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<State, Mutation, Action, Effect>(
    private var state: State,
    protected val mutations: MutableSharedFlow<Mutation> = MutableSharedFlow()
) : CoroutineScope by MainScope() {

    private val actionFlow = MutableSharedFlow<Action>()
    private val stateFlow = MutableStateFlow<State>(state)

    init {
        launch(Dispatchers.IO) {
            mutations.collect { m ->
                update(state, m).let { update ->
                    state = update.first
                    stateFlow.emit(state)
                    update.second?.forEach { e -> effect(e) }
                }
            }
        }
    }

    @CallSuper
    fun finish() {
        cancel()
    }

    fun mutation(mutation: Mutation) {
        launch { mutations.emit(mutation) }
    }

    fun getStateFlow() = stateFlow

    protected fun action(action: Action) {
        launch { actionFlow.emit(action) }
    }

    protected abstract fun update(state: State, mutation: Mutation): Pair<State, List<Effect>?>

    protected abstract fun effect(effect: Effect)

}