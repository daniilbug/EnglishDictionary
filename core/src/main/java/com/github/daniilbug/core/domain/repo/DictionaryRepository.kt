package com.github.daniilbug.core.domain.repo

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.dict.DictionaryError
import com.github.daniilbug.core.domain.model.DictionaryAnswerDomain
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRepository(
    private val remoteDataSource: DictionaryRemoteDataSource
) {

    fun getDefinitions(word: String): Flow<BinaryResult<DictionaryError, DictionaryAnswerDomain>> {
        return flow {
            emit(remoteDataSource.getDefinitions(word))
        }
    }
}