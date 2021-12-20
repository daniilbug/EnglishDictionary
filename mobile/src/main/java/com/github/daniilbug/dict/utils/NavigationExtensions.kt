package com.github.daniilbug.dict.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import kotlinx.coroutines.flow.collect

object Screen {
    object Dictionary {
        const val name = "dictionary"
    }

    object Search {
        const val name = "search"
    }

    object Definition {
        const val name = "definition"
        const val word = "word"
    }

    object Image {
        const val name = "image"
        const val url = "url"
    }
}

fun AppScreen.toRoute(): String {
    return when (this) {
        AppScreen.DictionaryList -> Screen.Dictionary.name
        AppScreen.Search -> Screen.Search.name
        is AppScreen.Definition -> Screen.Definition.name + "/" + word
        is AppScreen.Image -> Screen.Image.name + "?${Screen.Image.url}=" + imageUrl
    }
}

@Composable
fun rememberRouterNavController(): NavHostController {
    val navController = rememberNavController()
    val router = router()

    LaunchedEffect(Unit) {
        router.commandFlow.collect { command ->
            when (command) {
                Command.Back -> navController.popBackStack()
                is Command.Open -> navController.navigate(command.screen.toRoute())
                is Command.Replace -> {
                    navController.popBackStack()
                    navController.navigate(command.screen.toRoute())
                }
            }
        }
    }

    return navController
}