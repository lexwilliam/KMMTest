package com.lexwilliam.kmmtest.presenter.add

import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.model.TransactionType
import com.lexwilliam.kmmtest.presenter.detail.DetailIntent

sealed interface AddIntent {
    data class InsertTransaction(val transaction: Transaction): AddIntent

    data class TypeChanged(val type: TransactionType): AddIntent

    data class ValueChanged(val value: Double): AddIntent

    data class NameChanged(val name: String): AddIntent

    data class DescChanged(val desc: String): AddIntent
}