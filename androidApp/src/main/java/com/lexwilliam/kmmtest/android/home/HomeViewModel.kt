package com.lexwilliam.kmmtest.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.lexwilliam.kmmtest.presenter.home.HomeEffect
import com.lexwilliam.kmmtest.presenter.home.HomeIntent
import com.lexwilliam.kmmtest.presenter.home.HomeState
import com.lexwilliam.kmmtest.presenter.home.HomeStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val store: HomeStore
): ViewModel() {
    val uiState = store
        .states
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState()
        )

    val sideEffects: Flow<HomeEffect?> = store.labels

    fun onAddTapped() {
        store.accept(HomeIntent.AddTransaction)
    }
}