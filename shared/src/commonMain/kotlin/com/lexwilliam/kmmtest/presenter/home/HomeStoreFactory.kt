package com.lexwilliam.kmmtest.presenter.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.lexwilliam.kmmtest.domain.TransactionRepository
import com.lexwilliam.kmmtest.domain.model.Transaction
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class HomeStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: TransactionRepository
) {
    fun create(): HomeStore {
        return object :
            HomeStore,
            Store<HomeIntent, HomeState, HomeEffect>
            by storeFactory.create(
                name = "Home",
                bootstrapper = SimpleBootstrapper(Unit),
                initialState = HomeState(),
                executorFactory = ::ListExecutor,
                reducer = ListReducer,
            ) {}
    }

    private sealed interface Message {
        data class Loaded(val transactions: List<Transaction>): Message
    }

    private inner class ListExecutor: CoroutineExecutor<HomeIntent, Unit, HomeState, Message, HomeEffect>() {

        private var job: Job? = null

        override fun executeAction(action: Unit, getState: () -> HomeState) {
            handleDataLoaded()
        }

        override fun executeIntent(intent: HomeIntent, getState: () -> HomeState) {
            when (intent) {
                HomeIntent.AddTransaction -> publish(HomeEffect.NavigateToAdd)
                else -> {}
            }
        }

        private fun handleDataLoaded() {
            job?.cancel()
            job = repository
                .getAllTransactions()
                .onEach { transactions -> dispatch(Message.Loaded(transactions)) }
                .launchIn(scope)
        }

    }

    private object ListReducer: Reducer<HomeState, Message> {
        override fun HomeState.reduce(msg: Message): HomeState =
            when (msg) {
                is Message.Loaded -> copy(transactions = msg.transactions, isLoading = false)
            }

    }



}