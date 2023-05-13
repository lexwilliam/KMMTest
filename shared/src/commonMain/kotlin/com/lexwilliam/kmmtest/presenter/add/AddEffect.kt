package com.lexwilliam.kmmtest.presenter.add

sealed interface AddEffect {
    object NavigateToHome: AddEffect
}