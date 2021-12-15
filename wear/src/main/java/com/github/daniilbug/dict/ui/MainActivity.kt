package com.github.daniilbug.dict.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.Navigator
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import com.github.daniilbug.dict.ui.screen.DictionaryScreen
import com.github.daniilbug.dict.ui.screen.SearchScreen
import com.github.daniilbug.dict.utils.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalWearMaterialApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberRouterNavController()

                SwipeDismissableRouterNavHost(
                    navController = navController,
                    startScreen = AppScreen.DictionaryList
                ) {
                    composable(AppScreen.DictionaryList.toRoute()) {
                        DictionaryScreen(viewModel = daggerViewModel())
                    }
                    composable(AppScreen.Search.toRoute()) {
                        SearchScreen(viewModel = daggerViewModel())
                    }
                }
            }
        }
    }
}