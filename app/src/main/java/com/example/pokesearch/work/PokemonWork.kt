package com.example.pokesearch.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokesearch.R
import com.example.pokesearch.database.getDatabase
import com.example.pokesearch.repository.PokemonRepository
import timber.log.Timber

class PokemonWork(appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "COMBEE_MALE"
    }

    private val database = getDatabase(appContext)
    private val pokemonRepository = PokemonRepository(database)

    override suspend fun doWork(): Result {
        Timber.i("Starting work...")
        val names = applicationContext.resources.getStringArray(R.array.names_for_adapter)
        val count: Int = pokemonRepository.addAllPokemonToDatabase(names)
        Timber.i("ending work..")
        if (count != 516) {
            Timber.tag("PokemonWork").e("Not all pokemon were added. Retrying...")
            return Result.retry()
        } else {
            Timber.tag("PokemonWork").i("$count Pokemon were downloaded.")

            return Result.success()
        }
    }
}