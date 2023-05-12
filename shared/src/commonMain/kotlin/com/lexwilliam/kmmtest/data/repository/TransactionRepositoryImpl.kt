package com.lexwilliam.kmmtest.data.repository

import com.lexwilliam.kmmtest.data.TransactionLocalSource
import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val transactionLocalSource: TransactionLocalSource
): TransactionRepository {
    override suspend fun clearDatabase() {
        transactionLocalSource.clearDatabase()
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionLocalSource.getAllTransactions()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        return transactionLocalSource.insertTransaction(transaction)
    }

}