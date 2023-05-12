package com.lexwilliam.kmmtest.presenter.home

sealed interface HomeEffect {
    object NavigateToAdd: HomeEffect
}