package com.github.daniilbug.dict.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.github.daniilbug.core.core.LambdaViewModelFactory
import com.github.daniilbug.dict.ui.screen.DefinitionsScreen
import com.github.daniilbug.dict.ui.screen.DictionaryScreen
import com.github.daniilbug.dict.ui.screen.SearchScreen
import com.github.daniilbug.dict.utils.*

@OptIn(ExperimentalWearMaterialApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberRouterNavController()

                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = Screen.Search.name
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
                }
            }
        }
    }
}