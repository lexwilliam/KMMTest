package com.lexwilliam.kmmtest.presenter.detail

import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.model.TransactionType

sealed interface DetailIntent {
    data class LoadTransaction(val id: String): DetailIntent

    object UpdateTransaction: DetailIntent

    data class TypeChanged(val type: TransactionType): DetailIntent

    data class ValueChanged(val value: Double): DetailIntent

    data class NameChanged(val name: String): DetailIntent

    data class DescChanged(val desc: String): DetailIntent

    object DeleteTransaction: DetailIntent

    object ArgsNotFound: DetailIntent
}