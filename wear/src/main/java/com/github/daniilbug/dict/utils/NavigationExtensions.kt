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

private const val DICTIONARY_LIST = "dictionary"
private const val SEARCH = "search"

fun AppScreen.toRoute(): String {
    return when(this) {
        AppScreen.DictionaryList -> DICTIONARY_LIST
        AppScreen.Search -> SEARCH
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun rememberRouterNavController(): NavHostController {
    val navController = rememberSwipeDismissableNavController()
    val router = router()

    LaunchedEffect(Unit) {
        router.commandFlow.collect { command ->
            when(command) {
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

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeDismissableRouterNavHost(
    navController: NavHostController,
    startScreen: AppScreen,
    builder: NavGraphBuilder.() -> Unit
) = SwipeDismissableNavHost(
    navController = navController,
    startDestination = startScreen.toRoute(),
    builder = builder
)