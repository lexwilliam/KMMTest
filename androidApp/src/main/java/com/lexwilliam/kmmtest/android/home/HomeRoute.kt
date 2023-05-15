package com.lexwilliam.kmmtest.android.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lexwilliam.kmmtest.android.di.viewModelModule
import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.presenter.home.HomeEffect
import com.lexwilliam.kmmtest.presenter.home.HomeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    toAdd: () -> Unit,
    toDetail: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffects.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let { effect ->
            when (effect) {
                HomeEffect.NavigateToAdd -> toAdd()
                is HomeEffect.NavigateToDetail -> toDetail(effect.id)
            }
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Transactions") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onAddTapped) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transactions")
            }
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
                else -> HomeScreen(state = uiState, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items = state.transactions) { transaction ->
            TransactionItemView(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onTransactionTapped(transaction.id.uuidString)
                    },
                transaction = transaction
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@ComposeCompilerApi
@Composable
fun TransactionItemView(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    ListItem(
        modifier = modifier,
        overlineText = { Text(text = transaction.id.uuidString) },
        text = { Text(text = transaction.name) },
        secondaryText = { Text(text = transaction.desc) },
        trailing = { Text(text = transaction.value.toString()) }
    )
}