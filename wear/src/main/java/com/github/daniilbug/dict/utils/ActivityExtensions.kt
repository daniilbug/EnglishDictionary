package com.github.daniilbug.dict.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.daniilbug.core.core.LambdaViewModelFactory
import com.github.daniilbug.core.navigation.FlowAppRouter
import com.github.daniilbug.dict.WatchApp
import com.github.daniilbug.dict.di.WatchAppComponent

val component: WatchAppComponent
    @Composable
    get() = WatchApp.component(
        LocalContext.current.applicationContext
    )

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
): VM {
    val factory = component.getViewModelFactory()
    return viewModel(viewModelStoreOwner, factory = factory)
}

@Composable
inline fun <reified VM: ViewModel> argumentViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    crossinline viewModelCreator: (WatchAppComponent) -> ViewModel
): VM {
    val appComponent = component
    return viewModel(
        viewModelStoreOwner,
        factory = LambdaViewModelFactory { viewModelCreator(appComponent) }
    )
}

@Composable
fun router(): FlowAppRouter {
    return component.getFlowRouter()
}