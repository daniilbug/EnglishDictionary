package com.github.daniilbug.dict.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

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
}

fun AppScreen.toRoute(): String {
    return when (this) {
        AppScreen.DictionaryList -> Screen.Dictionary.name
        AppScreen.Search -> Screen.Search.name
        is AppScreen.Definition -> Screen.Definition.name + "/" + word
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun rememberRouterNavController(): NavHostController {
    val navController = rememberSwipeDismissableNavController()
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