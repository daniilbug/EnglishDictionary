package com.github.daniilbug.core.core.string

import android.content.Context
import javax.inject.Inject

class ResourcesStringResolver @Inject constructor(
    context: Context
): StringResolver {

    private val resources = context.resources

    override fun getString(id: Int): String {
        return resources.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return resources.getString(id, args)
    }
}