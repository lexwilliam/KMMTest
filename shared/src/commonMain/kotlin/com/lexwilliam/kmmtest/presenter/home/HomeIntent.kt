package com.lexwilliam.kmmtest.presenter.home

import com.lexwilliam.kmmtest.domain.model.Transaction

sealed interface HomeIntent {
    object AddTransaction: HomeIntent

    object DeleteAllTransactions: HomeIntent
}