package com.lexwilliam.kmmtest.data

import com.lexwilliam.kmmtest.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionLocalSource {
    suspend fun clearDatabase()

    fun getAllTransactions(): Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun getTransactionById(id: String): Transaction

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(id: String)


}