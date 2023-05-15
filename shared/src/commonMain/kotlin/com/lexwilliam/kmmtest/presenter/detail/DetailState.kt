package com.lexwilliam.kmmtest.presenter.detail

import com.lexwilliam.kmmtest.domain.model.KmmUUID
import com.lexwilliam.kmmtest.domain.model.TransactionType

data class DetailState(
    val id: KmmUUID = KmmUUID(),
    val name: String = "",
    val desc: String = "",
    val type: TransactionType = TransactionType.EMPTY,
    val valueText: String = "",
    val isLoading: Boolean = true,
    val isError: Boolean = false
)