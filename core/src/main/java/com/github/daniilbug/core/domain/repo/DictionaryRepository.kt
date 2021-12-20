package com.github.daniilbug.core.domain.repo

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.DictionaryError
import com.github.daniilbug.core.domain.model.DefinitionDomain
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRepository(
    private val remoteDataSource: DictionaryRemoteDataSource
) {

    fun getDefinitions(word: String): Flow<BinaryResult<DictionaryError, List<DefinitionDomain>>> {
        return flow {
            emit(remoteDataSource.getDefinitions(word))
        }
    }
}