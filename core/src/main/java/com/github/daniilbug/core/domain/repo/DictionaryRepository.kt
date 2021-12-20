package com.github.daniilbug.core.domain.repo

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.dict.DictionaryError
import com.github.daniilbug.core.domain.model.DictionaryAnswerDomain
import com.github.daniilbug.core.domain.model.SearchItemDomain
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import com.github.daniilbug.core.domain.source.SearchHistoryLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class DictionaryRepository(
    private val dictionaryRemoteDataSource: DictionaryRemoteDataSource,
    private val historyLocalDataSource: SearchHistoryLocalDataSource
) {

    fun getDefinitions(word: String): Flow<BinaryResult<DictionaryError, DictionaryAnswerDomain>> {
        return flow {
            emit(dictionaryRemoteDataSource.getDefinitions(word))
        }.onEach { result ->
            if (result is BinaryResult.Success) {
                val firstDefinition = result.data.definitions.firstOrNull() ?: return@onEach
                historyLocalDataSource.addSearchItem(
                    SearchItemDomain(word, firstDefinition.definition)
                )
            }
        }
    }

    fun getSearchHistory(): Flow<List<SearchItemDomain>> {
        return historyLocalDataSource.getSearchHistory()
    }
}