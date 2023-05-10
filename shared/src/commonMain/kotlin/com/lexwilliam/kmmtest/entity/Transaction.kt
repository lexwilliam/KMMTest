package com.lexwilliam.kmmtest.entity

data class Transaction(
    val KMMuid: Long,
    val name: String,
    val desc: String,
    val type: TransactionType,
    val value: Long
)

enum class TransactionType {
    INCOME, EXPENSE, TRANSFER, EMPTY
}