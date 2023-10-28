package com.example.pokesearch.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.utils.parsePokemonFromDex
import com.example.pokesearch.utils.parsePokemonJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class SearchViewModel: ViewModel() {
    private val TAG = "Cynthia"

    init {
        viewModelScope.launch {
            val pkmn = PokemonApi.pokemonRetrofitService

            val pokemonResponse = pkmn.getPokemonById(915)
            val pokemon = parsePokemonJsonResult(JSONObject(pokemonResponse))

            val pokemonListResponse = pkmn.getDex(31)
            val pokemonNames = parsePokemonFromDex(JSONObject(pokemonListResponse))

            val pokemonListNew = pkmn.getDex(32)
            val pokemonNamesNew = parsePokemonFromDex(JSONObject(pokemonListNew))
/*
            Log.e(TAG, "The name is ${pokemon.name}")
            Log.e(TAG, "The dexNUm is ${pokemon.dexNum}")
            Log.e(TAG, "The types is ${pokemon.types?.type1} and ${pokemon.types?.type2}")
            Log.e(TAG, "The abilites are ${pokemon.abilities.ability1}, ${pokemon.abilities.ability2}, and ${pokemon.abilities.ability3}")
            Log.e(TAG, "The moves is ${pokemon.moves}")
            Log.e(TAG, "The sprite is ${pokemon.sprite}")
            Log.e(TAG, "The stats is ${pokemon.stats}")*/
            Log.i(TAG, "The list is $pokemonNames")
            Log.i(TAG, "The list is $pokemonNamesNew")

        }
    }
}