package com.lexwilliam.kmmtest.presenter.add

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.lexwilliam.kmmtest.domain.TransactionRepository
import com.lexwilliam.kmmtest.domain.model.Transaction
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class AddStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: TransactionRepository
) {
    fun create(): AddStore {
        return object :
            AddStore,
            Store<AddIntent, AddState, AddEffect>
            by storeFactory.create(
                name = "Add",
                bootstrapper = SimpleBootstrapper(Unit),
                initialState = AddState(),
                executorFactory = ::ListExecutor,
//                reducer = ListReducer
            ) {}
    }

    private sealed interface Message {
//        data class TransactionAdded(val transactions: Transaction): Message
    }

    private inner class ListExecutor: CoroutineExecutor<AddIntent, Unit, AddState, Message, AddEffect>() {

        private var job: Job? = null

        override fun executeAction(action: Unit, getState: () -> AddState) {}

        override fun executeIntent(intent: AddIntent, getState: () -> AddState) {
            when (intent) {
                is AddIntent.InsertTransaction -> handleInsertTransaction(intent.transaction)
            }
        }

        private fun handleInsertTransaction(transaction: Transaction) {
            scope.launch {
                repository.insertTransaction(transaction)
                publish(AddEffect.NavigateToHome)
            }
        }

        override fun dispose() {
            job?.cancel()
            job = null
            super.dispose()
        }

    }

//    private object ListReducer: Reducer<AddState, Message> {
//        override fun AddState.reduce(msg: Message): AddState {
//
//        }
//
//    }



}