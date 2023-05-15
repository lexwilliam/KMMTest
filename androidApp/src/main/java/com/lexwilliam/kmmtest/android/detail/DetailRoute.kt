package com.lexwilliam.kmmtest.android.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lexwilliam.kmmtest.android.add.TransactionTextField
import com.lexwilliam.kmmtest.android.add.TypeChipGroup
import com.lexwilliam.kmmtest.android.home.HomeScreen
import com.lexwilliam.kmmtest.presenter.detail.DetailEffect
import com.lexwilliam.kmmtest.presenter.detail.DetailState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailRoute(
    toHome: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffects.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let { effect ->
            when (effect) {
                DetailEffect.NavigateToHome -> toHome()
                DetailEffect.TransactionError -> {}
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                actions = {
                    Text(
                        modifier = Modifier
                            .clickable {
                                viewModel.onSaveTapped()
                            },
                        text = "Save"
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.isError -> ErrorScreen(msg = "Transaction Id Not Found")
                else -> DetailScreen(
                    state = uiState,
                    viewModel = viewModel
                )
            }
        }
    }

}

@Composable
fun DetailScreen(
    state: DetailState,
    viewModel: DetailViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TypeChipGroup(
            activeType = state.type,
            onTypeChanged = { viewModel.onTypeChanged(it) }
        )
        TransactionTextField(
            value = state.value.toString(),
            onValueChange = { viewModel.onValueChanged(it.toDouble()) }
        )
        TransactionTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChanged(it) }
        )
        TransactionTextField(
            value = state.desc,
            onValueChange = { viewModel.onDescChanged(it) }
        )
        DeleteButton(
            onClick = { viewModel.onDeleteTapped() }
        )
    }
}

@Composable
fun DeleteButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick
    ) {
        Text("Delete Transaction")
    }
}

@Composable
fun ErrorScreen(msg: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = msg)
    }
}