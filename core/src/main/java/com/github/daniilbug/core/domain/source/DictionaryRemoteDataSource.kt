package com.github.daniilbug.core.domain.source

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.DictionaryError
import com.github.daniilbug.core.domain.model.DefinitionDomain

interface DictionaryRemoteDataSource {
    suspend fun getDefinitions(word: String): BinaryResult<DictionaryError, List<DefinitionDomain>>
}