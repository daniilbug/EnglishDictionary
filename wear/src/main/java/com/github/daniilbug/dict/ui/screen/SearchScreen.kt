package com.github.daniilbug.dict.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.app.RemoteInput
import android.content.Intent
import com.github.daniilbug.dict.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import com.github.daniilbug.core.presentation.search.SearchEvent
import com.github.daniilbug.core.presentation.search.SearchViewModel

private const val SEARCH_QUERY = "SEARCH_QUERY"

@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    val state by viewModel.state.collectAsState()
    DictionarySearch(
        query = state.searchQuery,
        onQueryUpdate = { query -> viewModel.sendEvent(SearchEvent.UpdateQuery(query)) },
        onSuccessClick = { viewModel.sendEvent(SearchEvent.Search) }
    )
}

@Composable
private fun DictionarySearch(
    query: String,
    onQueryUpdate: (newQuery: String) -> Unit,
    onSuccessClick: () -> Unit
) {
    val inputLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it.data?.let { data ->
            val results = RemoteInput.getResultsFromIntent(data)
            val newQuery = results.getCharSequence(SEARCH_QUERY)
            onQueryUpdate(newQuery.toString())
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (query.isNotEmpty()) {
                Text(text = query)
                Spacer(modifier = Modifier.height(8.dp))
                SuccessButtons(
                    onSuccessClick = onSuccessClick,
                    onClearClick = { onQueryUpdate("") }
                )
            } else {
                SearchButton(onSearchClick = { inputLauncher.launch(createInputIntent()) })
            }
        }
    }
}

@Composable
private fun SearchButton(
    onSearchClick: () -> Unit
) {
    Button(
        onClick = onSearchClick,
        modifier = Modifier.size(ButtonDefaults.LargeButtonSize)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.content_description_search)
        )
    }
}

@Composable
private fun SuccessButtons(
    onSuccessClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Row {
        Button(
            onClick = onClearClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_clear),
                contentDescription = stringResource(id = R.string.content_description_clear)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onSuccessClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_success),
                contentDescription = stringResource(id = R.string.content_description_success)
            )
        }
    }
}

private fun createInputIntent(): Intent {
    val intent = RemoteInputIntentHelper.createActionRemoteInputIntent();
    val remoteInputs: List<RemoteInput> = listOf(
        RemoteInput.Builder(SEARCH_QUERY)
            .build()
    )
    RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
    return intent
}

@Preview("SearchScreen")
@Composable
private fun SearchScreenPreview() {
    DictionarySearch(
        query = "Search",
        onQueryUpdate = { },
        onSuccessClick = { }
    )
}