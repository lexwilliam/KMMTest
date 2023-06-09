package com.lexwilliam.kmmtest.android.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lexwilliam.kmmtest.domain.model.Transaction
import com.lexwilliam.kmmtest.domain.model.TransactionType
import com.lexwilliam.kmmtest.presenter.add.AddEffect
import com.lexwilliam.kmmtest.presenter.add.AddState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRoute(
    toHome: () -> Unit,
    viewModel: AddViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sideEffect by viewModel.sideEffects.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let { effect ->
            when (effect) {
                AddEffect.NavigateToHome -> toHome()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Add") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            AddScreen(
                state = uiState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun AddScreen(
    state: AddState,
    viewModel: AddViewModel
) {
    val transaction = Transaction(
        name = state.name,
        desc = state.desc,
        type = state.type,
        value = if (state.valueText == "") 0.0 else state.valueText.toDouble()
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TypeChipGroup(
            activeType = state.type,
            onTypeChanged = { viewModel.onTypeChanged(it) },
        )
        TransactionTextField(
            value = state.valueText,
            onValueChange = { viewModel.onValueChanged(it) },
            label = "Value",
            isNum = true
        )
        TransactionTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = "Name",
            isNum = false
        )
        TransactionTextField(
            value = state.desc,
            onValueChange = { viewModel.onDescChanged(it) },
            label = "Description",
            isNum = false
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                viewModel.onInsertTapped(transaction)
            },
            enabled = checkTransaction(transaction)
        ) {
            Text("Add Transaction")
        }
    }
}

private fun checkTransaction(
    transaction: Transaction
): Boolean {
    return !(transaction.name.isBlank() || transaction.desc.isBlank() ||
            transaction.type == TransactionType.EMPTY || transaction.value == 0.0)
}


@Composable
fun TransactionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isNum: Boolean
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        label = { Text(text = label)},
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = if (isNum) KeyboardOptions(keyboardType = KeyboardType.Decimal)
            else KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun TypeChipGroup(
    activeType: TransactionType,
    onTypeChanged: (TransactionType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val chipModifier = Modifier.weight(1f).width(48.dp)
        TypeChip(
            modifier = chipModifier,
            type = TransactionType.INCOME,
            activeType = activeType,
            onClick = onTypeChanged
        )
        TypeChip(
            modifier = chipModifier,
            type = TransactionType.EXPENSE,
            activeType = activeType,
            onClick = onTypeChanged
        )
        TypeChip(
            modifier = chipModifier,
            type = TransactionType.TRANSFER,
            activeType = activeType,
            onClick = onTypeChanged
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypeChip(
    modifier: Modifier = Modifier,
    type: TransactionType,
    activeType: TransactionType,
    onClick: (TransactionType) -> Unit
) {
    val isSelected = (activeType == type)
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        border = if (isSelected) BorderStroke(2.dp, Color.Black)
            else BorderStroke(0.dp, Color.Transparent),
        onClick = { onClick(type) }
    ) {
        Text(type.nm)
    }
}