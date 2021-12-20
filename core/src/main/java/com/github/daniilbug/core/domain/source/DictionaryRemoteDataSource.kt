package com.github.daniilbug.core.domain.source

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.dict.DictionaryError
import com.github.daniilbug.core.domain.model.DefinitionDomain
import com.github.daniilbug.core.domain.model.DictionaryAnswerDomain

interface DictionaryRemoteDataSource {
    suspend fun getDefinitions(word: String): BinaryResult<DictionaryError, DictionaryAnswerDomain>
}