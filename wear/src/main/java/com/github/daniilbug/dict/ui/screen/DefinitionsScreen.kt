package com.github.daniilbug.dict.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import coil.compose.rememberImagePainter
import com.github.daniilbug.core.presentation.definition.DefinitionUI
import com.github.daniilbug.core.presentation.definition.DefinitionsEvent
import com.github.daniilbug.core.presentation.definition.DefinitionsState
import com.github.daniilbug.core.presentation.definition.DefinitionsViewModel
import com.github.daniilbug.dict.R

@Composable
fun DefinitionsScreen(viewModel: DefinitionsViewModel) {
    val state by viewModel.state.collectAsState()
    when (val currentState = state) {
        is DefinitionsState.Definitions -> DefinitionsList(
            word = currentState.word,
            imageUrl = currentState.imageUrl,
            definitions = currentState.definitions,
            onHeaderImageClick = { url ->
                viewModel.sendEvent(DefinitionsEvent.OpenImage(url))
            }
        )
        is DefinitionsState.Error -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(currentState.message)
        }
        DefinitionsState.Loading -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(stringResource(id = R.string.loading))
        }
    }
}

@Composable
private fun DefinitionsList(
    word: String,
    imageUrl: String?,
    definitions: List<DefinitionUI>,
    onHeaderImageClick: (imageUrl: String) -> Unit
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
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item(key = word) {
                Text(word)
            }
            if (imageUrl != null) {
                item(key = imageUrl) {
                    HeaderImage(
                        word = word,
                        imageUrl = imageUrl,
                        onClick = { onHeaderImageClick(imageUrl) }
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(
                definitions,
                key = { definition -> definition.definition }
            ) { definition ->
                TitleCard(
                    title = { Text(definition.partOfSpeech) },
                    onClick = { }
                ) {
                    Text(definition.definition)
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HeaderImage(
    word: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Image(
        painter = rememberImagePainter(imageUrl) {
            crossfade(true)
        },
        contentDescription = word,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(132.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    )
}

@Preview("Definitions Screen")
@Composable
private fun DefinitionsScreenPreview() {
    DefinitionsList(
        word = "Preview",
        imageUrl = "",
        definitions = List(10) { index ->
            DefinitionUI(
                partOfSpeech = "Part $index",
                definition = "Definition $index"
            )
        },
        onHeaderImageClick = { }
    )
}