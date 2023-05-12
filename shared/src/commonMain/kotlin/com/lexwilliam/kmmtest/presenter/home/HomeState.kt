package com.lexwilliam.kmmtest.presenter.home

import com.lexwilliam.kmmtest.domain.model.Transaction

data class HomeState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false
)