package com.example.pokesearch

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class PokemonApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    //TODO Add work to download the database and figure out how often it needs to do so
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
}