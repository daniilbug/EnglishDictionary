package com.github.daniilbug.dict

import android.app.Application
import android.content.Context
import com.github.daniilbug.core.di.DaggerCoreComponent
import com.github.daniilbug.dict.di.DaggerWatchAppComponent
import com.github.daniilbug.dict.di.WatchAppComponent

class WatchApp: Application() {

    companion object {
        fun component(appContext: Context): WatchAppComponent {
            return (appContext as WatchApp).component ?:
                error("App component hasn't been initialized yet")
        }
    }

    private var component: WatchAppComponent? = null

    override fun onCreate() {
        super.onCreate()
        component = DaggerWatchAppComponent.builder()
            .coreComponent(
                DaggerCoreComponent.builder()
                    .context(this)
                    .build()
            )
            .build()
    }
}