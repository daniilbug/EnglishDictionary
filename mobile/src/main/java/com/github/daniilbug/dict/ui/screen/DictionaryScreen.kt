package com.github.daniilbug.dict.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.daniilbug.core.presentation.dictionary.DictionaryEvent
import com.github.daniilbug.core.presentation.dictionary.DictionaryItemUI
import com.github.daniilbug.core.presentation.dictionary.DictionaryState
import com.github.daniilbug.core.presentation.dictionary.DictionaryViewModel
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
                CircularProgressIndicator()
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
                onAddWord = { viewModel.sendEvent(DictionaryEvent.OpenSearch) },
                onOpenItem = { item -> viewModel.sendEvent(DictionaryEvent.Search(item.word)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WordsList(
    dictionaryItems: List<DictionaryItemUI>,
    onAddWord: () -> Unit,
    onOpenItem: (item: DictionaryItemUI) -> Unit
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddWord
            ) {
                Icons.Filled.Search
                Icon(Icons.Filled.Search, contentDescription = stringResource(id = R.string.search))
            }
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(6.dp),
            state = lazyListState
        ) {
            items(dictionaryItems, key = { item -> item.word }) { item ->
                Card(
                    onClick = { onOpenItem(item) }
                ) {

                }
            }
        }
    }
}

@Composable
@Preview(name = "DictionaryScreen")
private fun DictionaryScreenPreview() {
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