package com.lexwilliam.kmmtest.presenter.home

sealed interface HomeEffect {
    object NavigateToAdd: HomeEffect

    data class NavigateToDetail(val id: String): HomeEffect
}