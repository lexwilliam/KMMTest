package com.lexwilliam.kmmtest.android.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.model.TransactionType
import com.lexwilliam.kmmtest.presenter.add.AddEffect
import com.lexwilliam.kmmtest.presenter.add.AddIntent
import com.lexwilliam.kmmtest.presenter.add.AddState
import com.lexwilliam.kmmtest.presenter.add.AddStore
import com.lexwilliam.kmmtest.presenter.detail.DetailIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AddViewModel(
    private val store: AddStore
): ViewModel() {
    val uiState = store
        .states
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddState()
        )

    val sideEffects: Flow<AddEffect?> = store.labels

    fun onInsertTapped(transaction: Transaction) {
        store.accept(AddIntent.InsertTransaction(transaction))
    }

    fun onNameChanged(name: String) {
        store.accept(AddIntent.NameChanged(name))
    }

    fun onDescChanged(desc: String) {
        store.accept(AddIntent.DescChanged(desc))
    }

    fun onTypeChanged(type: TransactionType) {
        store.accept(AddIntent.TypeChanged(type))
    }

    fun onValueChanged(value: Double) {
        store.accept(AddIntent.ValueChanged(value))
    }

}