package com.github.daniilbug.dict.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.daniilbug.core.presentation.search.SearchEvent
import com.github.daniilbug.core.presentation.search.SearchViewModel
import com.github.daniilbug.dict.R

@Composable
fun SearchDialog(viewModel: SearchViewModel) {
    val state by viewModel.state.collectAsState()

    SearchDialog(
        query = state.searchQuery,
        onNewQuery = { newQuery -> viewModel.sendEvent(SearchEvent.UpdateQuery(newQuery)) },
        onSearch = { viewModel.sendEvent(SearchEvent.Search) },
        onDismiss = { viewModel.sendEvent(SearchEvent.Back) }
    )
}

@Composable
private fun SearchDialog(
    query: String,
    onNewQuery: (newQuery: String) -> Unit,
    onSearch: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.wrapContentHeight()
            ) {
                Text(stringResource(id = R.string.search))
                TextField(
                    value = query,
                    onValueChange = { newText -> onNewQuery(newText) }
                )
            }
        },
        buttons = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                onClick = onSearch
            ) {
                Text(stringResource(id = R.string.find_it))
            }
        },
        onDismissRequest = onDismiss
    )
}

@Composable
@Preview("SearchDialog")
private fun SearchDialogPreview() {
    SearchDialog(
        query = "Query",
        onNewQuery = {},
        onSearch = {},
        onDismiss = {}
    )
}