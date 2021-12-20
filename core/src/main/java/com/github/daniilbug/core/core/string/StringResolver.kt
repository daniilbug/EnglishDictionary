package com.github.daniilbug.core.core.string

interface StringResolver {
    fun getString(id: Int): String
    fun getString(id: Int, vararg args: Any): String
}