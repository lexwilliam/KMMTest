package com.lexwilliam.kmmtest.presenter.detail

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.lexwilliam.kmmtest.domain.TransactionRepository
import com.lexwilliam.kmmtest.domain.model.KmmUUID
import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.model.TransactionType
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: TransactionRepository
) {
    fun create(): DetailStore {
        return object :
            DetailStore,
            Store<DetailIntent, DetailState, DetailEffect>
            by storeFactory.create(
                name = "Detail",
                bootstrapper = SimpleBootstrapper(Unit),
                initialState = DetailState(),
                executorFactory = ::ListExecutor,
                reducer = ListReducer
            ) {}
    }

    private sealed interface Message {
        data class Loaded(val transaction: Transaction): Message
        object ArgsNotFounded: Message
        data class NameChanged(val name: String): Message
        data class DescChanged(val desc: String): Message
        data class TypeChanged(val type: TransactionType): Message
        data class ValueChanged(val value: Double): Message
    }

    private inner class ListExecutor: CoroutineExecutor<DetailIntent, Unit, DetailState, DetailStoreFactory.Message, DetailEffect>() {

        private var job: Job? = null

        override fun executeAction(action: Unit, getState: () -> DetailState) {

        }

        override fun executeIntent(intent: DetailIntent, getState: () -> DetailState) {
            when (intent) {
                is DetailIntent.LoadTransaction -> handleTransactionLoaded(intent.id)
                DetailIntent.UpdateTransaction -> handleTransactionUpdated(getState())
                DetailIntent.DeleteTransaction -> handleTransactionDeleted(getState())
                DetailIntent.ArgsNotFound -> dispatch(Message.ArgsNotFounded)
                is DetailIntent.NameChanged -> dispatch(Message.NameChanged(intent.name))
                is DetailIntent.DescChanged -> dispatch(Message.DescChanged(intent.desc))
                is DetailIntent.TypeChanged -> dispatch(Message.TypeChanged(intent.type))
                is DetailIntent.ValueChanged -> dispatch(Message.ValueChanged(intent.value))
            }
        }

        private fun handleTransactionLoaded(id: String) {
            scope.launch {
                val transaction = repository.getTransactionById(id)
                dispatch(Message.Loaded(transaction))
            }
        }

        private fun handleTransactionUpdated(state: DetailState) {
            scope.launch {
                val transaction = mapTransaction(
                    state.id,
                    state.name,
                    state.desc,
                    state.type,
                    state.valueText.toDouble()
                )
                if (checkTransaction(transaction)) {
                    repository.updateTransaction(transaction)
                    publish(DetailEffect.NavigateToHome)
                } else {
                    publish(DetailEffect.TransactionError)
                }

            }
        }

        private fun checkTransaction(
            transaction: Transaction
        ): Boolean {
            return !(transaction.name.isBlank() || transaction.desc.isBlank() ||
                    transaction.type == TransactionType.EMPTY || transaction.value == 0.0)
        }

        private fun handleTransactionDeleted(state: DetailState) {
            scope.launch {
                repository.deleteTransactionById(state.id.uuidString)
                publish(DetailEffect.NavigateToHome)
            }
        }

        private fun mapTransaction(
            id: KmmUUID,
            name: String,
            desc: String,
            type: TransactionType,
            value: Double
        ): Transaction =
            Transaction(id, name, desc, type, value)

        override fun dispose() {
            job?.cancel()
            job = null
            super.dispose()
        }

    }

    private object ListReducer: Reducer<DetailState, Message> {
        override fun DetailState.reduce(msg: Message): DetailState {
            return when (msg) {
                is Message.Loaded -> copy(
                    id = msg.transaction.id,
                    name = msg.transaction.name,
                    desc = msg.transaction.desc,
                    type = msg.transaction.type,
                    valueText = msg.transaction.value.toString(),
                    isLoading = false
                )
                Message.ArgsNotFounded -> copy(
                    isError = true
                )
                is Message.NameChanged -> copy(
                    name = msg.name
                )
                is Message.DescChanged -> copy(
                    desc = msg.desc
                )
                is Message.TypeChanged -> copy(
                    type = msg.type
                )
                is Message.ValueChanged -> copy(
                    valueText = msg.value.toString()
                )
            }
        }
    }
}