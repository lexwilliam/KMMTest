package com.lexwilliam.kmmtest.presenter.home

import com.lexwilliam.kmmtest.domain.model.Transaction

sealed interface HomeIntent {
    object GetTransactions: HomeIntent

    data class InsertTransaction(val transaction: Transaction): HomeIntent

    object DeleteAllTransactions: HomeIntent
}