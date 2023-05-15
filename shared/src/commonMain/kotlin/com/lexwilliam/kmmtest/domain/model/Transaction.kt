package com.lexwilliam.kmmtest.domain.model

data class Transaction(
    val id: KmmUUID = KmmUUID(),
    var name: String,
    var desc: String,
    var type: TransactionType,
    var value: Double
)

enum class TransactionType(val nm: String) {
    INCOME("Income"),
    EXPENSE("Expense"),
    TRANSFER("Transfer"),
    EMPTY("Empty")
}