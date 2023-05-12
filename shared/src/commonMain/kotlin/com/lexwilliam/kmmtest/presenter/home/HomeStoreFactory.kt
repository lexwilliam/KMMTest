package com.lexwilliam.kmmtest.presenter.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.lexwilliam.kmmtest.domain.TransactionRepository
import kotlinx.coroutines.Job

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
    }

    private inner class ListExecutor: CoroutineExecutor<HomeIntent, Unit, HomeState, Message, HomeEffect>() {

        private var job: Job? = null

        override fun executeAction(action: Unit, getState: () -> HomeState) {
            super.executeAction(action, getState)
        }

        override fun executeIntent(intent: HomeIntent, getState: () -> HomeState) {
            super.executeIntent(intent, getState)
        }

    }

    private object ListReducer: Reducer<HomeState, Message> {
        override fun HomeState.reduce(msg: Message): HomeState {
            TODO("Not yet implemented")
        }

    }



}