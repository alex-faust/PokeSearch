package com.example.pokesearch.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.api.asDatabaseModel
import com.example.pokesearch.database.PokeDatabase
import com.example.pokesearch.database.asDomainModel
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.utils.parsePokemonJsonResult
import com.example.pokesearch.utils.retrieveQuery
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber
import java.util.SortedSet

class PokemonRepository(private val database: PokeDatabase) {
    private val TAG = "PokemonRepository"
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var query: SimpleSQLiteQuery = retrieveQuery()
    private var databaseNameToAdd: String = ""

    val pokemonResult: LiveData<List<Pokemon>> = database.pokemonDao.getPokemon(query).map {
        it.asDomainModel()
    }

    //TODO() Issue #2
    suspend fun addAllPokemonToDatabase(names: Array<String>): Int {
        var count = 0
        withContext(ioDispatcher) {

            Timber.tag(TAG).i("the size is ${names.size}")
            try {
                for (i in 0 until names.size) { //currently 516
                    databaseNameToAdd = names[i].lowercase()
                    val pokemonResponse =
                        PokemonApi.pokemonRetrofitService.getPokemonByName(databaseNameToAdd)
                    val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                    database.pokemonDao.insertPokemon(*pokemonList.asDatabaseModel())
                    count++
                }
            } catch (th: Throwable) {
                Timber.tag(TAG).i("All pokemon not added to database.")
                Timber.tag(TAG).i(th.localizedMessage)
            } catch (cancel: CancellationException) {
                Timber.tag(TAG).i(cancel.localizedMessage)
            }
        }
        return count
    }

    suspend fun getAllAbilities(names: Array<String>) {
        withContext(ioDispatcher) {
            val sortedSetList: SortedSet<String> = sortedSetOf<String>("")
            try {
                for (i in 0 until names.size) {
                    databaseNameToAdd = names[i].lowercase()
                    val pokemonResponse =
                        PokemonApi.pokemonRetrofitService.getPokemonByName(databaseNameToAdd)
                    val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                    val poke1 = pokemonList[0]

                    sortedSetList.add("<item>\"${poke1.abilities.ability1.replaceFirstChar { it.uppercase() }}\"</item> \n")
                    sortedSetList.add("<item>\"${poke1.abilities.ability2?.replaceFirstChar { it.uppercase() }}\"</item> \n")
                    sortedSetList.add("<item>\"${poke1.abilities.ability3?.replaceFirstChar { it.uppercase() }}\"</item> \n")
                }
                Log.i(TAG, "$sortedSetList")
            } catch (_: Exception) {

            }
        }
    }



}



