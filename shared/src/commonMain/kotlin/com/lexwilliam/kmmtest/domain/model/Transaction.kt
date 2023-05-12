package com.lexwilliam.kmmtest.domain.model

data class Transaction(
    val id: KmmUUID = KmmUUID(),
    val name: String,
    val desc: String,
    val type: TransactionType,
    val value: Long
)

enum class TransactionType {
    INCOME, EXPENSE, TRANSFER, EMPTY
}