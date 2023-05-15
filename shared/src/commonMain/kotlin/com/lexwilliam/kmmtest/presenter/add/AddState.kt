package com.lexwilliam.kmmtest.presenter.add

import com.lexwilliam.kmmtest.domain.model.TransactionType

data class AddState(
    var name: String = "",
    var desc: String = "",
    var type: TransactionType = TransactionType.EMPTY,
    var valueText: String = ""
)