package com.example.pokesearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.api.asDatabaseModel
import com.example.pokesearch.database.PokeDatabase
import com.example.pokesearch.database.asDomainModel
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.utils.parsePokemonJsonResult
import com.example.pokesearch.utils.retrievePokemonSearchedName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.UnknownHostException


class PokemonRepository(private val database: PokeDatabase) {
    private val TAG = "PokemonRepository"

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    //private val name: String = "sprigatito"
    private val named: String = retrievePokemonSearchedName()


    val pokemonResults: LiveData<List<Pokemon>> = database.pokemonDao
        .getSinglePokemonByName(named).map {
            it.asDomainModel()
        }

    //val listOfPokemon: LiveData<ArrayList<Pokemon>> = database.pokemonList

    suspend fun savePokemonToDB() {
        withContext(ioDispatcher) {
            try {
                val pokemonResponse = PokemonApi.pokemonRetrofitService.getPokemonByName(named)
                val pokemonList = parsePokemonJsonResult(JSONObject(pokemonResponse))
                Log.i(TAG, "pokemonList is $pokemonList")
                database.pokemonDao.insertAllPokemon(*pokemonList.asDatabaseModel())
            } catch (exception: UnknownHostException) {
                Log.e("find me", "No Network")
            }
        }
    }



}



