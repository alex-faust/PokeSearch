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
import com.example.pokesearch.utils.parsePokemonFromDex
import com.example.pokesearch.utils.parsePokemonJsonResult
import com.example.pokesearch.utils.retrievePokemonSearchedName
import com.example.pokesearch.utils.retrievePokemonSearchedQuery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.UnknownHostException


class PokemonRepository(private val database: PokeDatabase) {
    private val TAG = "PokemonRepository"
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val name: String = retrievePokemonSearchedName()
    private val query: SimpleSQLiteQuery = retrievePokemonSearchedQuery()
    private var databaseNameToAdd: String = ""

    //TODO(issue #2)
    val pokemonResult: LiveData<List<Pokemon>> = database.pokemonDao
        .getPokemonByName(name).map {
            it.asDomainModel()
        }

    /*
    val pokemonResult: LiveData<List<Pokemon>> = database.pokemonDao
        .getPokemon(query).map {
        it.asDomainModel()
    }
    */

    suspend fun savePokemonToDB() {
        withContext(ioDispatcher) {
            try {
                val pokemonResponse = PokemonApi.pokemonRetrofitService.getPokemonByName(name)
                val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                Log.d(TAG, "pokemon is $pokemonList")
                database.pokemonDao.insertPokemon(*pokemonList.asDatabaseModel())
            } catch (exception: UnknownHostException) {
                Log.d(TAG, "No Network")
            }
        }
    }

    //TODO(issue #1)
    suspend fun addAllPokemonToDatabase(names: Array<String>) {
        withContext(ioDispatcher) {
            Log.i(TAG, "the size is ${names.size}")

            try {
                for (element in names) {
                    databaseNameToAdd = element.replaceFirstChar { it.lowercase() }
                    val pokemonResponse = PokemonApi.pokemonRetrofitService.getPokemonByName(databaseNameToAdd)
                    val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                    database.pokemonDao.insertPokemon(*pokemonList.asDatabaseModel())
                }
            } catch (th: Throwable) {
              Log.i(TAG, "All pokemon not added to database.")
            }
        }
    }

    suspend fun getAllAvailablePokemonNames(): ArrayList<String> {
        var paldeaList: ArrayList<String>
        var kitanamiList: ArrayList<String>
        val pokemonList = ArrayList<String>()

        withContext(ioDispatcher) {
            try {
                val allPaldeaNames = PokemonApi.pokemonRetrofitService.getDex(31)
                paldeaList = parsePokemonFromDex(JSONObject(allPaldeaNames))
                val allKitanamiNames = PokemonApi.pokemonRetrofitService.getDex(32)
                kitanamiList= parsePokemonFromDex(JSONObject(allKitanamiNames))

                for (i in 0 until paldeaList.size) {
                    pokemonList.add("<item>\"${paldeaList[i].replaceFirstChar{it.uppercase()}}\"</item>\n")
                }
                for (i in 0 until kitanamiList.size) {
                    pokemonList.add("<item>\"${kitanamiList[i].replaceFirstChar{it.uppercase()}}\"</item>\n")
                }
            } catch (exception: UnknownHostException) {
                Log.e(TAG, "No Network")
            }
        }

        pokemonList.sort()

        return pokemonList
    }




}



