package com.lexwilliam.kmmtest.android.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.lexwilliam.kmmtest.domain.model.TransactionType
import com.lexwilliam.kmmtest.presenter.detail.DetailEffect
import com.lexwilliam.kmmtest.presenter.detail.DetailIntent
import com.lexwilliam.kmmtest.presenter.detail.DetailState
import com.lexwilliam.kmmtest.presenter.detail.DetailStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    private val store: DetailStore,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val uiState = store
        .states
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailState()
        )

    val sideEffects: Flow<DetailEffect?> = store.labels

    init {
        val transactionIdFromArgs = savedStateHandle.get<String>("transaction_id")
        if (transactionIdFromArgs != null) {
            onDataLoaded(transactionIdFromArgs)
        } else {
            onArgsNotFounded()
        }
    }

    private fun onDataLoaded(id: String) {
        store.accept(DetailIntent.LoadTransaction(id))
    }

    private fun onArgsNotFounded() {
        store.accept(DetailIntent.ArgsNotFound)
    }

    fun onNameChanged(name: String) {
        store.accept(DetailIntent.NameChanged(name))
    }

    fun onDescChanged(desc: String) {
        store.accept(DetailIntent.DescChanged(desc))
    }

    fun onTypeChanged(type: TransactionType) {
        store.accept(DetailIntent.TypeChanged(type))
    }

    fun onValueChanged(valueText: String) {
        val checkVal = valueText.toDoubleOrNull()
        if (checkVal != null) {
            store.accept(DetailIntent.ValueChanged(checkVal))
        }
    }

    fun onSaveTapped() {
        store.accept(DetailIntent.UpdateTransaction)
    }

    fun onDeleteTapped() {
        store.accept(DetailIntent.DeleteTransaction)
    }
}