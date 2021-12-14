package com.github.daniilbug.dict.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.daniilbug.dict.WatchApp

@Composable
inline fun <reified T : ViewModel> daggerViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
): T {
    val factory = WatchApp.component(
        LocalContext.current.applicationContext
    ).getViewModelFactory()

    return viewModel(viewModelStoreOwner, factory = factory)
}