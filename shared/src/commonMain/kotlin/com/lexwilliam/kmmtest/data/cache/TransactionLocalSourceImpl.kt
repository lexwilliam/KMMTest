package com.lexwilliam.kmmtest.data.cache

import com.lexwilliam.kmmtest.data.TransactionLocalSource
import com.lexwilliam.kmmtest.domain.model.DispatcherProvider
import com.lexwilliam.kmmtest.domain.model.KmmUUID
import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.model.TransactionType
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TransactionLocalSourceImpl(
    databaseDriverFactory: DatabaseDriverFactory,
    private val dispatcher: DispatcherProvider
): TransactionLocalSource {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    override suspend fun clearDatabase() {
        withContext(dispatcher.io()) {
            dbQuery.transaction {
                dbQuery.removeAllTransactions()
            }
        }
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dbQuery
            .selectAllTransactions(::mapTransaction)
            .asFlow()
            .mapToList()
            .flowOn(dispatcher.io())
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        withContext(dispatcher.io()) {
            dbQuery.insertTransaction(
                id = transaction.id.uuidString,
                name = transaction.name,
                desc = transaction.desc,
                type = toString(transaction.type),
                value_ = transaction.value
            )
        }
    }

    override suspend fun getTransactionById(id: String): Transaction {
        return dbQuery
            .selectTransactionById(id, ::mapTransaction)
            .executeAsOne()
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(dispatcher.io()) {
            dbQuery.updateTransaction(
                name = transaction.name,
                desc = transaction.desc,
                type = toString(transaction.type),
                value_ = transaction.value,
                id = transaction.id.uuidString
            )
        }
    }

    override suspend fun deleteTransactionById(id: String) {
        withContext(dispatcher.io()) {
            dbQuery.deleteTransactionById(id)
        }
    }

    private fun mapTransaction(
        id: String,
        name: String,
        desc: String,
        type: String,
        value: Double
    ): Transaction {
        return Transaction(
            KmmUUID.fromString(id), name, desc, toTransactionType(type), value
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
}