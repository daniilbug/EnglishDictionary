package com.github.daniilbug.dict.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.github.daniilbug.dict.ui.screen.DictionaryScreen
import com.github.daniilbug.dict.utils.daggerViewModel

@OptIn(ExperimentalWearMaterialApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold {
                val navController = rememberSwipeDismissableNavController()

                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = "dictionary"
                ) {
                    composable("dictionary") {
                        DictionaryScreen(viewModel = daggerViewModel())
                    }
                }
            }
        }
    }
}