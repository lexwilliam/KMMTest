package com.lexwilliam.kmmtest.presenter.detail

sealed interface DetailEffect {
    object NavigateToHome: DetailEffect
    object TransactionError: DetailEffect
}