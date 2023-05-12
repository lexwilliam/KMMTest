package com.lexwilliam.kmmtest.domain

import com.lexwilliam.kmmtest.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun clearDatabase()

    fun getAllTransactions(): Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)
}