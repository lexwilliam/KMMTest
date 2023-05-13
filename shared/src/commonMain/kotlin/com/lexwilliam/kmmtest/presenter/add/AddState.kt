package com.lexwilliam.kmmtest.presenter.add

import com.lexwilliam.kmmtest.domain.model.TransactionType

data class AddState(
    val name: String = "",
    val desc: String = "",
    val type: TransactionType = TransactionType.EMPTY,
    val value: Double = 0.0
)