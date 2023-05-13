package com.lexwilliam.kmmtest.domain.model

data class Transaction(
    val id: KmmUUID = KmmUUID(),
    val name: String,
    val desc: String,
    val type: TransactionType,
    val value: Double
)

enum class TransactionType(val nm: String) {
    INCOME("Income"),
    EXPENSE("Expense"),
    TRANSFER("Transfer"),
    EMPTY("Empty")
}