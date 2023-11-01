package com.example.pokesearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.database.PokeDatabase
import com.example.pokesearch.database.asDomainModel
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.utils.parsePokemonFromDex
import com.example.pokesearch.utils.parsePokemonJsonResult
import com.example.pokesearch.utils.retrievePokemonSearchedName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.UnknownHostException


class PokemonRepository(database: PokeDatabase) {
    private val TAG = "PokemonRepository"
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val named: String = retrievePokemonSearchedName()


    val pokemonResults: LiveData<List<Pokemon>> = database.pokemonDao
        .getSinglePokemonByName(named).map {
            it.asDomainModel()
        }

    suspend fun savePokemonToDB() {
        withContext(ioDispatcher) {
            try {
                val pokemonResponse = PokemonApi.pokemonRetrofitService.getPokemonByName(named)
                val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                //Log.i(TAG, "pokemon is $pokemonList")
                //database.pokemonDao.insertAllPokemon(*pokemonList.asDatabaseModel())
            } catch (exception: UnknownHostException) {
                Log.e(TAG, "No Network")
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



