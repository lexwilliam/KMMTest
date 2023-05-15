package com.lexwilliam.kmmtest.presenter.home

sealed interface HomeIntent {
    object AddTransaction: HomeIntent

    data class TransactionTapped(val id: String): HomeIntent
}