package com.example.pokesearch.pokemoninfo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokesearch.api.PokemonApi
import com.example.pokesearch.base.BaseViewModel
import com.example.pokesearch.model.Pokemon
import com.example.pokesearch.utils.parsePokemonJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class PokemonInfoViewModel : ViewModel() {




    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    init {
        getPokemonName()
    }

    private fun getPokemonName() {
        viewModelScope.launch {
            try {
                val jsonResult = PokemonApi.pokemonRetrofitService.getPokemonById(915)
                val poke = parsePokemonJsonResult(JSONObject(jsonResult))
                _pokemon.value = poke
                //Log.i("Pokemon", "The name is ${_pokemon.value.sprite}")
            } catch (e: Exception) {
                e.localizedMessage
            }
        }
    }




}