package com.github.daniilbug.dict

import android.app.Application
import android.content.Context
import com.github.daniilbug.dict.di.AppComponent
import com.github.daniilbug.dict.di.DaggerAppComponent

class App : Application() {

    companion object {
        fun component(appContext: Context): AppComponent {
            return (appContext as App).component
                ?: error("App component hasn't been initialized yet")
        }
    }

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}