package com.github.daniilbug.dict.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.daniilbug.core.presentation.definition.DefinitionUI
import com.github.daniilbug.core.presentation.definition.DefinitionsEvent
import com.github.daniilbug.core.presentation.definition.DefinitionsState
import com.github.daniilbug.core.presentation.definition.DefinitionsViewModel
import com.github.daniilbug.dict.R

@Composable
fun DefinitionsScreen(viewModel: DefinitionsViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.word) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.sendEvent(DefinitionsEvent.Back) }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) {
        when (val currentState = state) {
            is DefinitionsState.Loading -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
            is DefinitionsState.Error -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ErrorColumn(
                    imagePainter = rememberVectorPainter(image = Icons.Default.Warning),
                    text = currentState.message
                )
            }
            is DefinitionsState.NotFound -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ErrorColumn(
                    imagePainter = rememberVectorPainter(image = Icons.Default.Search),
                    text = stringResource(id = R.string.not_found)
                )
            }
            is DefinitionsState.Definitions -> {
                DefinitionsList(
                    word = currentState.word,
                    imageUrl = currentState.imageUrl,
                    definitions = currentState.definitions
                )
            }
        }
    }
}

@Composable
private fun DefinitionsList(
    word: String,
    imageUrl: String?,
    definitions: List<DefinitionUI>
) {
    LazyColumn {
        if (imageUrl != null) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(imageUrl) {
                            crossfade(true)
                        },
                        contentDescription = word,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        itemsIndexed(
            definitions,
            itemContent = { index, item ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Definition(definition = item)
                    if (index != definitions.lastIndex) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider()
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Definition(definition: DefinitionUI) {
    ListItem(
        text = { Text(definition.partOfSpeech) },
        secondaryText = { Text(definition.definition) }
    )
}

@Composable
private fun ErrorColumn(
    imagePainter: Painter,
    text: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = text,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier.size(120.dp)
        )
        Text(text)
    }
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
        }
    )
}