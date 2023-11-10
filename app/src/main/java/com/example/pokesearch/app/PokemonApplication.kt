package com.example.pokesearch.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.impl.model.Preference
import com.example.pokesearch.BuildConfig
import com.example.pokesearch.work.PokemonWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PokemonApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatRequest = PeriodicWorkRequestBuilder<PokemonWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            PokemonWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, repeatRequest
        )

        //val num = repeatRequest.id.toString()
        //val sharedPrefs: SharedPreferences = getSharedPreferences("prefID", Context.MODE_PRIVATE)
        //val editor: SharedPreferences.Editor = sharedPrefs.edit().putString("prefID", num)
        //editor.apply()
        //editor.commit()




    }
}