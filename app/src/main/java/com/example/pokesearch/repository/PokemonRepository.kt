package com.example.pokesearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.sqlite.db.SimpleSQLiteQuery
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


class PokemonRepository(private val database: PokeDatabase) {
    private val TAG = "PokemonRepository"
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var query: SimpleSQLiteQuery = retrieveQuery()
    private var databaseNameToAdd: String = ""

    val pokemonResult: LiveData<List<Pokemon>> = database.pokemonDao.getPokemon(query).map {
        it.asDomainModel()
    }

    //TODO(issue #1)
    suspend fun addAllPokemonToDatabase(names: Array<String>) {
        withContext(ioDispatcher) {
            Log.i(TAG, "the size is ${names.size}")

            try {
                for (i in 0 until names.size) { //currently 516
                    databaseNameToAdd = names[i].lowercase()
                    val pokemonResponse =
                        PokemonApi.pokemonRetrofitService.getPokemonByName(databaseNameToAdd)
                    val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                    database.pokemonDao.insertPokemon(*pokemonList.asDatabaseModel())
                    Log.i(TAG, "$i pokemon name is $pokemonList")
                }
            } catch (th: Throwable) {
                Log.i(TAG, "All pokemon not added to database.")
                Log.i(TAG, th.localizedMessage)
            } catch (cancel: CancellationException) {
                Log.i(TAG, cancel.localizedMessage)
            }
        }
    }

    /*suspend fun getAllAbilities(names: Array<String>) {
        withContext(ioDispatcher) {
            try {
                for (i in 0 until names.size) {
                    databaseNameToAdd = names[i].lowercase()
                    val pokemonResponse =
                        PokemonApi.pokemonRetrofitService.getPokemonByName(databaseNameToAdd)
                    val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                    database.pokemonDao.insertPokemon(*pokemonList.asDatabaseModel())
                }
            }
        }
    }*/
}



