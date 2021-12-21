package com.github.daniilbug.dict.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
private fun WordsList(
    dictionaryItems: List<DictionaryItemUI>,
    onAddWord: () -> Unit,
    onOpenItem: (item: DictionaryItemUI) -> Unit
) {
    var buttonVisible by remember { mutableStateOf(true) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                buttonVisible = available.y > 0
                return Offset.Zero
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = buttonVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { 3 * it / 2 })
            ) {
                FloatingActionButton(onClick = onAddWord) {
                    Icons.Filled.Search
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
            }
        },
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(dictionaryItems, key = { item -> item.word }) { item ->
                DictionaryItem(item = item, onOpenItem = onOpenItem)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DictionaryItem(
    item: DictionaryItemUI,
    onOpenItem: (item: DictionaryItemUI) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        onClick = { onOpenItem(item) }
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Column {
                Text(item.word, style = MaterialTheme.typography.subtitle1)
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                Text(item.definition, style = MaterialTheme.typography.subtitle2)
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