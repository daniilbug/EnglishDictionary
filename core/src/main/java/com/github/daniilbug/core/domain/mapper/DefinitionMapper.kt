package com.github.daniilbug.core.domain.mapper

import com.github.daniilbug.core.data.rest.model.DictionaryAnswerNetwork
import com.github.daniilbug.core.domain.model.DefinitionDomain

fun List<DictionaryAnswerNetwork>.asDomainDefinitions(): List<DefinitionDomain> {
    return this.flatMap { answer ->
        answer.meanings.flatMap { meaning ->
            meaning.definitions.map { definition ->
                DefinitionDomain(meaning.partOfSpeech, definition.definition)
            }
        }
    }
}