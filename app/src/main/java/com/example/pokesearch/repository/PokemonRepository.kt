package com.example.pokesearch.repository

import android.util.Log
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.api.asDatabaseModel
import com.example.pokesearch.database.PokemonDatabase
import com.example.pokesearch.utils.parsePokemonJsonResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.UnknownHostException


class PokemonRepository(private val database: PokemonDatabase) {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val name: String = "sprigatito"

    /*val pokemonByName: Pokemon = database.pokemonDao.getPokemonByName(name).map {
        it.asDomainModel()
    }*/


    suspend fun savePokemonToDB() {
        withContext(ioDispatcher) {
            try {
                val pokemonResponse = PokemonApi.pokemonRetrofitService.getPokemonByName(name)
                val pokemonResult = parsePokemonJsonResult(JSONObject(pokemonResponse))
                database.pokemonDao.insertPokemon(pokemonResult.asDatabaseModel())
            } catch (exception: UnknownHostException) {
                Log.e("find me", "No Network")
            }
        }
    }

    suspend fun deleleAllFromDB() {
        database.pokemonDao.deleteAllPokemonFromDB()
    }

}



