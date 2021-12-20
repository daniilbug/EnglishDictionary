package com.github.daniilbug.dict.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.github.daniilbug.dict.ui.screen.DefinitionsScreen
import com.github.daniilbug.dict.ui.screen.DictionaryScreen
import com.github.daniilbug.dict.ui.screen.ImageFullScreen
import com.github.daniilbug.dict.ui.screen.SearchScreen
import com.github.daniilbug.dict.ui.theme.EnglishDictionaryTheme
import com.github.daniilbug.dict.utils.Screen
import com.github.daniilbug.dict.utils.argumentViewModel
import com.github.daniilbug.dict.utils.daggerViewModel
import com.github.daniilbug.dict.utils.rememberRouterNavController

@OptIn(ExperimentalWearMaterialApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishDictionaryTheme {
                val navController = rememberRouterNavController()

                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = Screen.Dictionary.name
                ) {
                    composable(Screen.Dictionary.name) {
                        DictionaryScreen(viewModel = daggerViewModel())
                    }
                    composable(Screen.Search.name) {
                        SearchScreen(viewModel = daggerViewModel())
                    }
                    composable(
                        "${Screen.Definition.name}/{${Screen.Definition.word}}",
                    ) { backStackEntry ->
                        val word = backStackEntry.arguments?.getString(Screen.Definition.word)
                            ?: error("Argument ${Screen.Definition.word} was not found")
                        DefinitionsScreen(
                            argumentViewModel(viewModelCreator = { component ->
                                component.getDefinitionsViewModelCreator().create(word)
                            })
                        )
                    }
                    composable(
                        "${Screen.Image.name}?${Screen.Image.url}={${Screen.Image.url}}"
                    ) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString(Screen.Image.url)
                            ?: error("Argument ${Screen.Image.url} was not found")
                        ImageFullScreen(url)
                    }
                }
            }
        }
    }
}