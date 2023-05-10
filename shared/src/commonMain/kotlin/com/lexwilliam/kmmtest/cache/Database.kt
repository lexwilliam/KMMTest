package com.lexwilliam.kmmtest.cache

import com.lexwilliam.kmmtest.entity.Transaction
import com.lexwilliam.kmmtest.entity.TransactionType

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllTransactions()
        }
    }

    internal fun getAllTransactions(): List<Transaction> {
        return dbQuery.selectAllTransactions(::mapTransactions).executeAsList()
    }

    private fun mapTransactions(
        KMMuid: Long,
        name: String,
        desc: String,
        type: String,
        value: Long
    ): Transaction {
        return Transaction(
            KMMuid, name, desc, toTransactionType(type), value
        )
    }

    private fun toTransactionType(type: String): TransactionType {
        return when (type) {
            "INCOME" -> TransactionType.INCOME
            "EXPENSE" -> TransactionType.EXPENSE
            "TRANSFER" -> TransactionType.TRANSFER
            else -> TransactionType.EMPTY
        }
    }

    private fun toString(type: TransactionType): String {
        return when (type) {
            TransactionType.INCOME -> "INCOME"
            TransactionType.EXPENSE -> "EXPENSE"
            TransactionType.TRANSFER -> "TRANSFER"
            else -> "EMPTY"
        }
    }

    internal fun insertTransaction(transaction: Transaction) {
        dbQuery.insertTransaction(
            KMMuid = transaction.KMMuid,
            name = transaction.name,
            desc = transaction.desc,
            type = toString(transaction.type),
            value_ = transaction.value
        )
    }
}