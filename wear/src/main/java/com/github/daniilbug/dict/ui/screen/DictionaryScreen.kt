package com.github.daniilbug.dict.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.github.daniilbug.core.presentation.DictionaryItemUI
import com.github.daniilbug.core.presentation.DictionaryState
import com.github.daniilbug.core.presentation.DictionaryViewModel
import com.github.daniilbug.dict.R

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel
) {
    val state by viewModel.state.collectAsState()
    when (val currentState = state) {
        DictionaryState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(stringResource(id = R.string.loading))
            }
        }
        is DictionaryState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(currentState.message)
            }
        }
        is DictionaryState.DictionaryItems -> {
            WordsList(
                dictionaryItems = currentState.items,
                onAddWord = { },
                onOpenItem = { }
            )
        }
    }
}

@Composable
private fun WordsList(
    dictionaryItems: List<DictionaryItemUI>,
    onAddWord: () -> Unit,
    onOpenItem: (item: DictionaryItemUI) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberScalingLazyListState()
    Scaffold(
        positionIndicator = {
            if (lazyListState.isScrollInProgress) {
                PositionIndicator(lazyListState)
            }
        }
    ) {
        ScalingLazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(6.dp),
            state = lazyListState,
            modifier = modifier
        ) {
            item {
                AddChip(
                    onAddWord = onAddWord
                )
            }
            items(dictionaryItems, key = DictionaryItemUI::word) { item ->
                DictionaryItemCard(
                    item = item,
                    onOpenItem = onOpenItem
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun AddChip(onAddWord: () -> Unit) {
    Chip(
        modifier = Modifier.fillMaxWidth(0.5f),
        label = {
            Text(
                stringResource(id = R.string.add),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        onClick = onAddWord
    )
}

@Composable
private fun DictionaryItemCard(
    item: DictionaryItemUI,
    onOpenItem: (item: DictionaryItemUI) -> Unit
) {
    TitleCard(
        onClick = { onOpenItem(item) },
        title = { Text(item.word) }
    ) {
        Text(
            text = item.definition,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
@Preview(name = "DictionaryScreen")
fun DictionaryScreenPreview() {
    val previewItems = List(10) { index ->
        DictionaryItemUI(
            word = "Word $index",
            definition = "Definition $index"
        )
    }

    WordsList(
        dictionaryItems = previewItems,
        onAddWord = { },
        onOpenItem = { }
    )
}