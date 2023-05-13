package com.lexwilliam.kmmtest.presenter.add

import com.lexwilliam.kmmtest.domain.model.Transaction

sealed interface AddIntent {
    data class InsertTransaction(val transaction: Transaction): AddIntent
}